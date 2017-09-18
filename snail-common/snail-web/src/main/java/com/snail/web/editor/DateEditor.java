package com.snail.web.editor;

/**
 * Created by Administrator on 2017/9/15.
 */
import com.snail.common.util.TimeUtils;

import java.beans.PropertyEditorSupport;

/**
 */
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        setValue(TimeUtils.parseDate(text));
    }

}