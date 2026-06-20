package com.codehub.chatroom.server.handler;

import com.codehub.chatroom.common.message.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 聊天业务 Handler（阶段 2）
 *
 * 职责：处理已认证用户的所有消息
 * - CHAT:    广播给所有在线用户
 * - PRIVATE: 转发给目标用户
 * - COMMAND: 处理命令（改名等）
 *
 * 注意：此 Handler 只收到已通过 AuthHandler 认证的消息
 */
public class ChatHandler extends SimpleChannelInboundHandler<Message> {

    /** 所有在线 Channel，用于广播 */
    private static final ChannelGroup channels =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /** 昵称 → Channel 映射，用于私聊 */
    private static final ConcurrentMap<String, Channel> nameChannelMap =
            new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        // TODO: 根据消息类型分发处理
        // 提示:
        // switch (msg) {
        //     case ChatMessage chat    -> handleChat(ctx, chat);
        //     case PrivateMessage pm   -> handlePrivate(ctx, pm);
        //     case CommandMessage cmd  -> handleCommand(ctx, cmd);
        //     case SystemMessage sys   -> { /* 服务端不处理 SystemMessage */ }
        // }
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 广播聊天消息
     */
    private void handleChat(ChannelHandlerContext ctx, ChatMessage msg) {
        // TODO: 广播给所有在线用户（channels.writeAndFlush）
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 私聊消息转发
     */
    private void handlePrivate(ChannelHandlerContext ctx, PrivateMessage msg) {
        // TODO:
        // 1. 从 nameChannelMap 找目标 Channel
        // 2. 找到 → 转发消息
        // 3. 找不到 → 给发送者发 SystemMessage 提示 "用户不在线"
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 处理命令
     */
    private void handleCommand(ChannelHandlerContext ctx, CommandMessage msg) {
        // TODO: 根据 command 字段分发
        // "name" → 改名逻辑
        //   a. 校验新昵称
        //   b. 从 nameChannelMap 移除旧昵称
        //   c. 更新 Channel 属性
        //   d. 加入 nameChannelMap
        //   e. 广播 "旧名 改名为 新名"
        throw new UnsupportedOperationException("待实现");
    }

    /**
     * 连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO:
        // 1. 获取昵称: ctx.channel().attr(AuthHandler.NICKNAME_KEY).get()
        // 2. 从 nameChannelMap 移除
        // 3. 从 channels 移除
        // 4. 广播下线通知
        throw new UnsupportedOperationException("待实现");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
