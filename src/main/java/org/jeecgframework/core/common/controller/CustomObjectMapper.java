
package org.jeecgframework.core.common.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @description 解决@ResponseBody返回json数据，DATE格式
 * @author scott
 * @date 2013-5-28
 */
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		SimpleModule module = new SimpleModule();
		module.addSerializer(Date.class, new JsonSerializer<Date>() {

			@Override
			public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider)
					throws IOException {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = sdf.format(value);
				if (dateStr.endsWith(" 00:00:00")) {
					dateStr = dateStr.substring(0, 10);
				} else if (dateStr.endsWith(":00")) {
					dateStr = dateStr.substring(0, 16);
				}
				jsonGenerator.writeString(dateStr);
			}
		});
		this.registerModule(module);
	}
}
