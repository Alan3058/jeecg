
package org.jeecgframework.core.timer;

import java.text.ParseException;

import org.jeecgframework.web.system.pojo.base.TSTimeTaskEntity;
import org.jeecgframework.web.system.service.TimeTaskServiceI;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

/**
 * 在原有功能的基础上面增加数据库的读取
 * @author JueYue
 * @date 2013-9-22
 * @version 1.0
 */
public class DataBaseCronTriggerBean extends CronTriggerFactoryBean {

	private static final long serialVersionUID = 1L;
	@Autowired
	private TimeTaskServiceI timeTaskService;

	/**
	 * 读取数据库更新文件
	 */
	public void afterPropertiesSet() throws ParseException {
		super.afterPropertiesSet();
		CronTriggerImpl cronTrigger = (CronTriggerImpl) this.getObject();
		TSTimeTaskEntity task = timeTaskService.findUniqueByProperty(TSTimeTaskEntity.class, "taskId",
				cronTrigger.getName());
		if (task != null && task.getIsEffect().equals("1")
				&& !task.getCronExpression().equals(cronTrigger.getCronExpression())) {
			this.setCronExpression(task.getCronExpression());
			// DynamicTask.updateSpringMvcTaskXML(this,task.getCronExpression());
		}
	}
}
