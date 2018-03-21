package com.xzy.grpc.client;

import com.xzy.grpc.service.domain.GreeterGrpc;
import com.xzy.grpc.service.domain.Helloworld;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class HelloWorldClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;


    public HelloWorldClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();

        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public  void greet(String name){
        Helloworld.HelloRequest request = Helloworld.HelloRequest.newBuilder().setName(name).build();
        Helloworld.HelloReply response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());
        
        response = blockingStub.doEcho(request);
        System.out.println(response.getMessage());

    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClient client = new HelloWorldClient("127.0.0.1",50001);
        for(int i=0;i<5;i++){
            client.greet("world:"+i);
        }
    }
}
