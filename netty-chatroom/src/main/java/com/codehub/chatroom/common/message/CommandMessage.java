package com.codehub.chatroom.common.message;

/**
 * 命令消息
 *
 * 客户端发送 → 服务端处理
 * 用途：改名(/name)、退出(/quit)
 */
public final class CommandMessage extends Message {

    private final String command;
    private final String arg;

    public CommandMessage(String sender, String command, String arg) {
        super(MessageType.COMMAND, sender);
        this.command = command;
        this.arg = arg;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    @Override
    public String toString() {
        return String.format("[命令] %s: %s %s", getSender(), command, arg != null ? arg : "");
    }
}
