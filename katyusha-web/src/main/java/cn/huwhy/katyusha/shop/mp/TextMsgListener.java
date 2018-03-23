package cn.huwhy.katyusha.shop.mp;

import cn.huwhy.wx.sdk.listener.Listener;
import cn.huwhy.wx.sdk.message.Message;
import cn.huwhy.wx.sdk.message.ReplyMsgBuilder;
import cn.huwhy.wx.sdk.message.TextMessage;
import cn.huwhy.wx.sdk.model.Command;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static cn.huwhy.wx.sdk.model.Command.EVENT_KEYS.TEXT_MSG;

@Component
public class TextMsgListener extends Listener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String handle(Command command) {
        logger.info("command: {}", JSON.toJSONString(command));
        if (command.getCommandKey().equals(TEXT_MSG)) {
            Message message = new TextMessage();
            message.setToUserName(command.getFromUserName());
            message.setFromUserName(command.getToUserName());
            message.setCreateTime(command.getCreateTime());
            message.setContent(command.getContent());
            return ReplyMsgBuilder.toXml(message);
        }
        return "";
    }
}
