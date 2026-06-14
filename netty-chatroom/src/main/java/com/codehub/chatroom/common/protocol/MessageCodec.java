package com.codehub.chatroom.common.protocol;

import com.codehub.chatroom.common.message.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * 消息编解码的纯逻辑（不依赖 Netty Handler）
 *
 * 协议格式: [Length(4B)] [Type(1B)] [JSON Body]
 * - Length: 不包含自身 4 字节
 * - Type:   MessageType 的 code 值
 * - Body:   Gson 序列化的 JSON
 *
 * 被 MessageDecoder 和 MessageEncoder 共同调用
 */
public class MessageCodec {

    private static final Gson GSON = new Gson();

    /**
     * 编码：Message → 写入 ByteBuf（包含 Length + Type + JSON）
     */
    public void encode(Message msg, ByteBuf out) {
        // TODO: 实现编码
        // 步骤:
        // 1. 用 GSON 将 msg 序列化为 JSON 字符串
        // 2. 计算 body 长度 = 1 (type) + jsonBytes.length
        // 3. 写入 Length (4B, int)
        // 4. 写入 Type (1B, byte)
        // 5. 写入 JSON bytes
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 解码：ByteBuf（已剥掉 Length 头）→ Message
     * 入参 in 只包含 [Type(1B)] [JSON Body]
     */
    public Message decode(ByteBuf in) {
        // TODO: 实现解码
        // 步骤:
        // 1. 读取 type (1 字节)
        // 2. 读取剩余字节为 JSON 字符串
        // 3. 根据 type 选择目标类:
        //    CHAT(1)    → ChatMessage.class
        //    PRIVATE(2) → PrivateMessage.class
        //    SYSTEM(3)  → SystemMessage.class
        //    COMMAND(4) → CommandMessage.class
        // 4. 用 GSON 反序列化
        throw new UnsupportedOperationException("待实现");
    }
}
