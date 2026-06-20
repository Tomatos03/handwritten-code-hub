package com.codehub.chatroom.common.message;

/**
 * 私聊消息
 *
 * 客户端发送 → 服务端转发给目标用户
 */
public final class PrivateMessage extends Message {

    private final String target;
    private final String content;

    public PrivateMessage(String sender, String target, String content) {
        super(MessageType.PRIVATE, sender);
        this.target = target;
        this.content = content;
    }

    public String getTarget() {
        return target;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s → %s: %s", getType(), getSender(), target, content);
    }
}
