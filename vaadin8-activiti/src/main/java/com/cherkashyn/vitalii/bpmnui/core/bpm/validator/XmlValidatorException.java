package com.cherkashyn.vitalii.bpmnui.core.bpm.validator;

public class XmlValidatorException extends RuntimeException {
    public XmlValidatorException(String name) {
        super(name);
    }
    public XmlValidatorException(String name, Exception e) {
        super(name, e);
    }
}
