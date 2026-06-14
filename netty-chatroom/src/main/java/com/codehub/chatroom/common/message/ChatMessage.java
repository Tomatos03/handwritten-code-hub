package com.codehub.chatroom.common.message;

/**
 * 聊天消息（广播）
 *
 * 客户端发送 → 服务端广播给所有在线用户
 */
public final class ChatMessage extends Message {

    private final String content;

    public ChatMessage(String sender, String content) {
        super(MessageType.CHAT, sender);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", getType(), getSender(), content);
    }
}
