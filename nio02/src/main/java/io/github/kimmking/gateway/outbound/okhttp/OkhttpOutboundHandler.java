package io.github.kimmking.gateway.outbound.okhttp;

import io.github.kimmking.gateway.filter.HeaderHttpResponseFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.github.kimmking.gateway.outbound.httpclient4.NamedThreadFactory;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import okhttp3.*;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class OkhttpOutboundHandler {
    private List<String> backendUrls;
    private OkHttpClient okHttpClient;
    private ExecutorService proxyService;
    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    private static OkhttpOutboundHandler INSTANCE;

    public static OkhttpOutboundHandler getInstance(List<String> backends) {
        if (INSTANCE == null) {
            synchronized (OkhttpOutboundHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkhttpOutboundHandler(backends);
                }
            }
        }

        return INSTANCE;
    }

    private OkhttpOutboundHandler(List<String> backends) {
        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());

        int cores = Runtime.getRuntime().availableProcessors();
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        this.okHttpClient = builder.dispatcher(new Dispatcher(this.proxyService))
                .connectTimeout(Duration.ofMillis(1000))
                .callTimeout(Duration.ofMillis(1000))
                .build();
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String backendUrl = router.route(this.backendUrls);
        final String url = backendUrl + fullRequest.uri();
        filter.filter(fullRequest, ctx);

        Request request = new Request.Builder()
                .url(url)
                .build();
        HttpResponseFilter f = this.filter;
        this.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                FullHttpResponse res = null;
                try {
                    res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
                } finally {
                    if (res != null) {
                        if (!HttpUtil.isKeepAlive(fullRequest)) {
                            ctx.write(res).addListener(ChannelFutureListener.CLOSE);
                        } else {
                            ctx.write(res);
                        }
                    }
                    ctx.flush();
                    ctx.close();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FullHttpResponse res = null;
                try {
                    res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                            Unpooled.wrappedBuffer(response.body().bytes()));
                    FullHttpResponse finalRes = res;
                    response.headers().toMultimap()
                                    .entrySet()
                                            .forEach(entry -> finalRes.headers().set(entry.getKey(), entry.getValue()));
                    f.filter(res);
                } finally {
                    if (res != null) {
                        if (!HttpUtil.isKeepAlive(fullRequest)) {
                            ctx.write(res).addListener(ChannelFutureListener.CLOSE);
                        } else {
                            ctx.write(res);
                        }
                    }
                    ctx.flush();
                }
            }
        });
    }
}
