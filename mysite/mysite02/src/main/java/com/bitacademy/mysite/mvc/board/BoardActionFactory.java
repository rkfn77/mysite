package com.bitacademy.mysite.mvc.board;

import com.bitacademy.web.mvc.Action;
import com.bitacademy.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("writeform".equals(actionName)) {		
			action = new WriteFormAction();
		} else if("write".equals(actionName)) {		
			action = new WriteAction();
		}else if("delete".equals(actionName)) {		
			action = new DeleteAction();
		}else if("view".equals(actionName)) {		
			action = new ViewAction();
		}else if("modifyform".equals(actionName)) {		
			action = new ModifyFormAction();
		}else if("modify".equals(actionName)) {		
			action = new ModifyAction();
		}else {
			action = new ListAction();
		}
		
		return action;
	}

}