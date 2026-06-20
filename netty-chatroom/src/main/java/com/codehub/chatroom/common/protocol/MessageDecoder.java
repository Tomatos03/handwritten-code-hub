package com.codehub.chatroom.common.protocol;

import com.codehub.chatroom.common.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 入站解码器：字节 → Message 对象
 *
 * Pipeline 位置: LengthFieldBasedFrameDecoder 之后
 * 接收到的 ByteBuf 已经剥掉了 Length 头，只剩 [Type + JSON]
 */
public class MessageDecoder extends ByteToMessageDecoder {

    private final MessageCodec codec = new MessageCodec();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // TODO: 委托给 codec.decode(in)，将结果加入 out
        throw new UnsupportedOperationException("待实现");
    }
}
