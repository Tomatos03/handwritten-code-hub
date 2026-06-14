package com.codehub.chatroom.common.protocol;

import com.codehub.chatroom.common.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 出站编码器：Message 对象 → 字节
 *
 * Pipeline 位置: 在所有 Handler 之后，写出到网络前
 * 输出完整的 [Length + Type + JSON] 字节流
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    private final MessageCodec codec = new MessageCodec();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // TODO: 委托给 codec.encode(msg, out)
        throw new UnsupportedOperationException("待实现");
    }
}
