package com.codehub.chatroom.common.message;

/**
 * 消息基类（sealed class）
 *
 * 所有消息类型的公共字段：
 * - type: 消息类型
 * - sender: 发送者昵称
 * - timestamp: 消息时间戳
 *
 * 子类：ChatMessage, PrivateMessage, SystemMessage, CommandMessage
 */
public abstract sealed class Message
        permits ChatMessage, PrivateMessage, SystemMessage, CommandMessage {

    private final MessageType type;
    private final String sender;
    private final long timestamp;

    protected Message(MessageType type, String sender) {
        this.type = type;
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
    }

    public MessageType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
