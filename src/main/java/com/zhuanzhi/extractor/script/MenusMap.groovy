package com.zhuanzhi.extractor.script

import com.zhuanzhi.extractor.base.MapExtractor
import com.zhuanzhi.structuring.config.Config

class MenusMap extends MapExtractor {

    def lawRegex = "《中华人民共和国审计法》"
    def menus = ~/[一二三四五六七八九]、(?<menu>.*)/

    @Override
    HashMap<String, String> extractor(Config auditConfig, String context) {

        HashMap<String, String> model = new HashMap<String, String>()

        def partFlag = false
        def partName = ""
        def partLines = ""
        def line = ""
        def lines = context.split("\n")
        for (int i = 0; i < lines.length; i++) {
            line = lines[i]
//            println(line)

            if (i == lines.length - 1) {
                model.put(partName, partLines)
//                model.put('postTime', line)
            }

            // 找法律那一条
            if (i < 10 && line.find(this.lawRegex)) {
                model.put("Activity", line)
            }
            // 找一级菜单
            def matcher = line =~ menus
            if (matcher.matches()) {

                def hitName = matcher.group('menu')
                // 去除所有标点
                hitName = hitName.replaceAll("\\p{P}" , "")
                if (partName != hitName) {
                    if (partName != "") {
                        model.put(partName, partLines)
                        partLines = ""
                    }
                    partFlag = true
                    partName = auditConfig.structMap.get(hitName)
                }
//                println(partName + "    " + hitName)

            } else if (partFlag) {
                partLines += line + '\n'
            }
        }
        return model
    }

}
