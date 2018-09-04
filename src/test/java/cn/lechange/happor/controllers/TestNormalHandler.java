package cn.lechange.happor.controllers;

import cn.lechange.happor.annotation.Controller;
import cn.lechange.happor.controller.HttpNormalHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

@Controller(method="POST", uriPattern="^/test/(\\w+)")
public class TestNormalHandler extends HttpNormalHandler {
	
	@Override
	protected void handle(FullHttpRequest request, FullHttpResponse response) {
		// TODO Auto-generated method stub
		HttpPostRequestDecoder httpPostRequestDecoder = new HttpPostRequestDecoder(request);
		while(httpPostRequestDecoder.hasNext()) {
			InterfaceHttpData data = httpPostRequestDecoder.next();
			System.out.println(getUploadFileName(data));
		}
		String words = "hello world " + getUriParser().getSection(1);
		response.content().writeBytes(words.getBytes());
		response.headers().set("Content-Type", "text/plain");
		response.headers().set("Content-Length", response.content().readableBytes());
	}

	private String getUploadFileName(InterfaceHttpData data) {
		String content = data.toString();
		String temp = content.substring(0, content.indexOf("\n"));
		content = temp.substring(temp.lastIndexOf("=") + 2, temp.lastIndexOf("\""));
		return content;
	}

	@Override
	protected void atlast() {
		// TODO Auto-generated method stub
		
	}

}
