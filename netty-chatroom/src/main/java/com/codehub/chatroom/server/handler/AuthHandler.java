package com.codehub.chatroom.server.handler;

import com.codehub.chatroom.common.message.ChatMessage;
import com.codehub.chatroom.common.message.Message;
import com.codehub.chatroom.common.message.SystemMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * 认证 Handler（阶段 1）
 *
 * 职责：处理新连接的昵称设置
 *
 * 工作流程：
 * 1. 新连接建立，此 Handler 在 Pipeline 中
 * 2. 收到第一条消息 → 解析为昵称
 * 3. 昵称合法 → 注册到在线列表 → 从 Pipeline 移除自己
 * 4. 后续消息直接到达 ChatHandler
 *
 * 移除自己: ctx.pipeline().remove(this)
 */
public class AuthHandler extends SimpleChannelInboundHandler<Message> {

    /** Channel 属性 key，用于存储昵称 */
    public static final AttributeKey<String> NICKNAME_KEY = AttributeKey.valueOf("nickname");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO: 发送提示消息 "请输入昵称:"
        // ctx.writeAndFlush(new SystemMessage("system", "请输入昵称:"));
        throw new UnsupportedOperationException("待实现");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        // TODO: 处理昵称设置
        // 提示:
        // 1. 检查 msg 是否为 ChatMessage（用户输入昵称时发的是 ChatMessage）
        // 2. 提取昵称，校验（不为空、不重复）
        // 3. 校验失败 → 发送错误提示，return（留在阶段1）
        // 4. 校验成功:
        //    a. 设置 Channel 属性: ctx.channel().attr(NICKNAME_KEY).set(nickname)
        //    b. 加入 nameChannelMap
        //    c. 加入 channels (ChannelGroup)
        //    d. 移除自己: ctx.pipeline().remove(this)
        //    e. 广播上线通知
        //    f. 给当前用户发欢迎消息
        throw new UnsupportedOperationException("待实现");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
