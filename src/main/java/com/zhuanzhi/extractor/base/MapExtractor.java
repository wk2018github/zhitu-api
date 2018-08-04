package com.zhuanzhi.extractor.base;

import com.zhuanzhi.structuring.config.Config;

import java.util.HashMap;

public abstract class MapExtractor {
    public abstract HashMap<String, String> extractor(Config auditConfig, String context);
}
