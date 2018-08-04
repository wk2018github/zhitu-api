package com.zhuanzhi.extractor.script

import com.zhuanzhi.extractor.base.ModelExtractor
import com.zhuanzhi.structuring.model.AuditItem
import com.zhuanzhi.structuring.model.Enforcement
import com.zhuanzhi.structuring.model.EnforcementItem
import com.zhuanzhi.structuring.model.Model

class EnforcementModel extends ModelExtractor {

    private int num = 0
    @Override
    Model extractor(String context) {
        num = 0
        Enforcement enforcement = new Enforcement()
        enforcement.enforcementItems = this.getEnforcementItems(context)
        return enforcement
    }

    def timeRegex = ~/.*(\d{4}年).*/

    def getEnforcementItems(context) {

        def markRegex = ~/\（一\）.*/
        def matcher = context =~ markRegex
        if(matcher.count == 0) {
            return handleSituation3(context)
        }
        return null

//        if(context.contains("（一）人防审批情况")) {
//            handleSituation1(context)
//        }
    }

    def handleSituation1(context) {
        ArrayList<EnforcementItem> enforcementItemList = new ArrayList<>();

        EnforcementItem enforcementItem = null
        def lines = context.split("\n")
        def markRegex = ~/^\（.*\）.*/

        def typeRegex = ~/\d{4}年(.*?)(共计)/
        def targetRegex = ~/(.*?)\d{4}年/

        for(line in lines) {
            def matcherMark = line =~ markRegex
            if(matcherMark.count > 0 || ((String)line).contains("《")) {
                continue
            }
            System.out.println("line : " + line);

            def mainSen = line.split("，")[0]
            def typeMatcher = mainSen =~ typeRegex
            def targetMatcher = mainSen =~ targetRegex
            if(typeMatcher.count) {
                System.out.println("===type : " + typeMatcher[0][1])
            }
            if(targetMatcher.count) {
                System.out.println("===target : " + targetMatcher[0])
            }

        }

    }

    def handleSituation3(context) {

        ArrayList<EnforcementItem> enforcementItemList = new ArrayList<>();

        EnforcementItem enforcementItem = null
        def lines = context.split("\n")
        for(line in lines) {

            def matcherTime = line =~ timeRegex
            if(matcherTime.count > 0) {
                System.out.println(line)
//                continue
                System.out.println("time : " + matcherTime[0][1])

                def targetRegex = ~/，(.*?)累计结余/
                def matcherTarget =  line =~ targetRegex
                if(matcherTarget.count > 0) {
                    System.out.println("target : " + matcherTarget[0][1])

                    enforcementItem = new EnforcementItem();
                    enforcementItem.auditTime = matcherTime[0][1]
                    enforcementItem.auditTarget = matcherTarget[0][1]
                    enforcementItem.auditType = matcherTarget[0][1]
                    enforcementItem.auditItems = new ArrayList<AuditItem>();

                    def tokenRegex = ~/([^\d]*)(.*)[^\d|\.]*/
                    def items = line.split("。|；")

                    for (int i = 1; i < items.size(); i++) {
                        String item = items[i]

                        if(!item.contains("其中")) {
                            AuditItem auditItem = null
                            String[] tokens = item.split("，")
                            int p = -1;
                            for (int j = 0; j < tokens.size(); j++) {
                                String token = tokens[j]
                                def matcherToken = token =~ tokenRegex

                                String name = matcherToken[0][1]
                                String value = matcherToken[0][2]
                                System.out.println("no qz======name : " + name)
                                System.out.println("no qz======value : " + value)
                                System.out.println("======num : " + num)
                                num++
                                auditItem = new AuditItem(name, value)
                                enforcementItem.auditItems.add(auditItem)

                            }
                            continue
                        }

                        System.out.println("****item : " + item)

                        AuditItem auditItem = null
                        String[] tokens = item.split("，")
                        int p = -1;
                        for (int j = 0; j < tokens.size(); j++) {
                            String token = tokens[j]
                            if (token.contains("其中")) {
                                p = j
                                break
                            }
                        }

                        for (int j = 0; j < p; j++) {
                            String token = tokens[j]
                            System.out.println("token: " + token)
                            def matcherToken = token =~ tokenRegex

                            String name = matcherToken[0][1]
                            String value = matcherToken[0][2]
                            System.out.println("======name : " + name)
                            System.out.println("======value : " + value)
                            System.out.println("======num : " + num)
                            num++
                            auditItem = new AuditItem(name, value)
                            enforcementItem.auditItems.add(auditItem)
                        }

                        String qz = tokens[p]
                        String token = qz.split("：")[1]
                        System.out.println("qz token : " + token)
                        def matcherToken = token =~ tokenRegex
                        String name = matcherToken[0][1]
                        String value = matcherToken[0][2]
                        System.out.println("======name : " + name)
                        System.out.println("======value : " + value)
                        System.out.println("======num : " + num)
                        num++
                        auditItem.childs.add(new AuditItem(name, value))

                        for (int j = p + 1; j < tokens.size(); j++) {
                            String token_child = tokens[j]
                            System.out.println("token_child: " + token_child)
                            def matcherTokenChild = token_child =~ tokenRegex

                            name = matcherTokenChild[0][1]
                            value = matcherTokenChild[0][2]
                            System.out.println("======name : " + name)
                            System.out.println("======value : " + value)
                            System.out.println("======num : " + num)
                            num++

                            auditItem.childs.add(new AuditItem(name, value))
                        }

                    }

                }

            }
            if(enforcementItem != null) {
                System.err.println(enforcementItem.toString())
                enforcementItemList.add(enforcementItem)
            }
        }
        return enforcementItemList
    }

}