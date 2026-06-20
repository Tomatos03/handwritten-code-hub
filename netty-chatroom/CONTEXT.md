# Netty 聊天室 - 设计决策

## 功能范围

- 多客户端连接、消息广播
- 上下线通知
- 私聊（@某人）
- 昵称设置（/name）、退出（/quit）
- 不做：用户认证/注册、消息持久化、Web 端、文件传输

## 技术栈

- JDK 17（sealed class）
- Netty 4.1.108.Final
- Gson 2.11.0
- SLF4J Simple 2.0.13

## 协议设计

```
┌────────────┬───────────┬──────────────────┐
│ Length(4B)  │ Type(1B)  │   JSON Body      │
│ int        │ byte      │   UTF-8          │
└────────────┴───────────┴──────────────────┘
Length 不包含自身 4 字节
initialBytesToStrip=4，下游只收到 Type + JSON
```

## 消息类型

| 类型 | code | 方向 | 用途 |
|------|------|------|------|
| CHAT | 1 | 客户端→服务端→广播 | 聊天消息 |
| PRIVATE | 2 | 客户端→服务端→目标客户端 | 私聊 |
| SYSTEM | 3 | 服务端→客户端 | 系统通知（上下线、欢迎） |
| COMMAND | 4 | 客户端→服务端 | 命令（改名等） |

消息类层次（sealed class）：
```
Message (abstract sealed)
├── ChatMessage        (sender, content)
├── PrivateMessage     (sender, target, content)
├── SystemMessage      (sender, content)
└── CommandMessage     (sender, command, arg)
```

## 项目结构

```
netty-chatroom/
├── pom.xml
├── CONTEXT.md
└── src/main/java/com/codehub/chatroom/
    ├── server/
    │   ├── ChatServer.java              ← 启动类
    │   ├── ChatServerInitializer.java   ← Pipeline 组装
    │   └── handler/
    │       ├── AuthHandler.java         ← 昵称认证（阶段1）
    │       └── ChatHandler.java         ← 业务逻辑（阶段2）
    ├── common/
    │   ├── message/
    │   │   ├── Message.java             ← sealed 基类
    │   │   ├── ChatMessage.java
    │   │   ├── PrivateMessage.java
    │   │   ├── SystemMessage.java
    │   │   ├── CommandMessage.java
    │   │   └── MessageType.java         ← 枚举
    │   └── protocol/
    │       ├── MessageCodec.java        ← 编解码纯逻辑
    │       ├── MessageDecoder.java      ← ByteToMessageDecoder
    │       └── MessageEncoder.java      ← MessageToByteEncoder
    └── client/
        ├── ChatClient.java              ← 客户端启动
        ├── ChatClientInitializer.java   ← 客户端 Pipeline
        └── handler/
            └── ClientHandler.java       ← 收发处理
```

## Pipeline 设计

### 服务端
```
入站: LengthFieldBasedFrameDecoder → MessageDecoder → AuthHandler → ChatHandler
出站: MessageEncoder
```

### 客户端
```
入站: LengthFieldBasedFrameDecoder → MessageDecoder → ClientHandler
出站: MessageEncoder
```

## 客户端管理

- `ChannelGroup`（DefaultChannelGroup）：广播、自动清理断开连接
- `ConcurrentHashMap<String, Channel>`：昵称→Channel 映射，用于私聊

## 连接生命周期

两阶段认证：
1. `channelActive` → 提示输入昵称，不加入在线列表
2. `AuthHandler` 收到昵称 → 验证合法 → 加入在线列表 → 移除 AuthHandler
3. `ChatHandler` 处理后续所有聊天消息
4. `channelInactive` → 移除、广播下线
5. `exceptionCaught` → 打印日志、关闭连接

## 客户端输入解析

```
"普通消息"    → ChatMessage（广播）
"@Tom 你好"  → PrivateMessage（私聊）
"/name 新名" → CommandMessage（改名）
"/quit"      → 关闭连接
```

## 开发里程碑

1. 最小可运行（echo）：协议编解码 + ChatHandler echo
2. 客户端骨架：ChatClient + ClientHandler
3. 多客户端广播：ChannelGroup + 广播逻辑
4. 认证 + 系统消息：AuthHandler + 上下线通知
5. 私聊 + 改名 + 命令：完整功能
