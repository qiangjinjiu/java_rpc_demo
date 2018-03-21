package com.xzy.grpc.service;

import com.xzy.grpc.service.domain.GreeterGrpc;
import com.xzy.grpc.service.domain.Helloworld;
import com.xzy.grpc.service.domain.Helloworld.HelloReply;
import com.xzy.grpc.service.domain.Helloworld.HelloRequest;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class HelloWorldServer {

    private static final int PORT = 50001;

    private static Server server;

    public static void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .addService(new GreeterImpl())
                .build().start();
        System.out.println("service start...");
    }

    private static void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        start();
        blockUntilShutdown();
    }


    private static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(Helloworld.HelloRequest req, StreamObserver<Helloworld.HelloReply> responseObserver) {
            Helloworld.HelloReply reply = Helloworld.HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

		@Override
		public void doEcho(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
			Helloworld.HelloReply reply = Helloworld.HelloReply.newBuilder().setMessage("Echo " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
		}
        
        
    }
}
