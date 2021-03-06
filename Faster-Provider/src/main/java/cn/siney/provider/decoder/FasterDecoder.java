package cn.siney.provider.decoder;


import cn.siney.ObjectSerializerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class FasterDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() >= 4){
            int len = in.readInt();//读取字节长度
            if(in.readableBytes() >= len){
                byte[] bytes = new byte[len];
                in.readBytes(bytes);
                Object o = ObjectSerializerUtils.unSerialize(bytes);
                out.add(o);
                System.out.println(o);
            }
        }

    }
}
