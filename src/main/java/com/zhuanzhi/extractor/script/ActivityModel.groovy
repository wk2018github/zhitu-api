package com.zhuanzhi.extractor.script

import com.zhuanzhi.extractor.base.ModelExtractor
import com.zhuanzhi.structuring.model.Activity
import com.zhuanzhi.structuring.model.Model

class ActivityModel extends ModelExtractor {

    private int num = 0;

    @Override
    Model extractor(String context) {
        Activity activity = new Activity();
        activity.auditLaws = this.getAuditLaws(context)
        activity.auditTime2Event = this.getAuditTime2Event(context)
        activity.auditReporter = this.getAuditReporter(context)
        activity.auditTarget = this.getAuditTarget(context)
        activity.auditTime = this.getAuditTime(context)
        activity.auditIndustry = this.getAuditIndustry(context)
        activity.auditClassification = this.getAuditClassification(context)
        return activity
    }

    def getAuditLaws(context) {
        num = 0
        def lawName = "《中华人民共和国审计法》"
        def lawRegex = ~/.*《中华人民共和国审计法》(?<number>第.*条)(.*)规定[和及]*(?<other>.*).*/

        def lines = context.split('，')
        def targetLine = lines[0]
        ArrayList<String> resultList = new ArrayList<String>()
        def number = ""
        def other = ""
        def matcher = targetLine =~ lawRegex
        if (matcher.matches()) {
            number = matcher.group("number")
            other = matcher.group("other")
        }
        resultList.add(lawName + number)
        System.out.println("num : " + num)
        num++
        if (other != "") {
            resultList.add(other)
        }
        return resultList
    }

    def getAuditTime2Event(context){
        ArrayList<String> auditTime2Event = new ArrayList<String>()

        def lines = context.split('，')
        def targetLine = [lines[1], lines[2]]

        def timeRegex = ~/(\d{4}年)?\d*月\d*日((至|、)?(\d*月)?\d*日)?/
        for(line in targetLine){
            def matcher = line =~ timeRegex
            for (int i = 0; i< matcher.getCount(); i++){
                auditTime2Event.add(matcher[i][0])
                System.out.println("num : " + num)
                num++
            }
        }
       return auditTime2Event
    }

    def getAuditReporter(context){
        String auditReporter = null;


        def reporterRegex = ~/，([^，]*)派出审计组/

//        for(line in targetLine){
//            def matcher = line =~ reporterRegex
//            if(matcher.matches()) {
//                auditReporter = matcher.group("report")
////                System.out.println(auditReporter)
//            }
//        }

        def matcher = context =~ reporterRegex
        (0..<-matcher.count).each {
            auditReporter = matcher[it][1]
            System.out.println("reporter match : " + auditReporter)
            System.out.println("num : " + num)
            num++
        }

        if(auditReporter == null) {
            System.err.println("no match in getAuditReporter method")
        }

        return auditReporter
    }

    def getAuditTarget(context){
        String auditTarget = null;

//        def lines = context.split('，')
//        def targetLine = [lines[2], lines[3]]

        def targetRegex = ~/对(.*?)\d{4}年/

//        for(line in targetLine) {
////            System.out.println("line" + line)
//            def matcher = line =~ targetRegex
//            (0..<-matcher.count).each {
//                System.out.println("match" + matcher[it][1])
//            }
////            if(matcher.find()) {
//////                auditTarget = matcher.group("target")
////                System.out.println(matcher[0])
////            }
//        }

        def matcher = context =~ targetRegex
        (0..<-matcher.count).each {
            auditTarget = matcher[it][1]
            System.out.println("target match : " + auditTarget)
            System.out.println("num : " + num)
            num++
        }

        if(auditTarget == null) {
            System.err.println("no match in getAuditTarget method")
        }

        return auditTarget
    }

    def getAuditTime(context) {

        def auditTime = null
        String auditTarget = getAuditTarget(context)
        System.out.println("at : " + auditTarget)

        def timeRegex = ~/${auditTarget}(\d{4}年((至|到)*\d{4}年)*).*?审计/
//        System.out.println("context : " + context)

        def matcher = context =~ timeRegex
        (0..<-matcher.count).each {
            auditTime = matcher[it][1]
            System.out.println("time match : " + auditTime)
            System.out.println("num : " + num)
            num++
        }

        if(auditTime == null) {
            System.err.println("no match in getAuditTime method")
        }

        return auditTime
    }

    def getAuditIndustry(context) {

        String auditIndustry = null

        String auditTime = getAuditTime(context)

        def industryRegex = ~/${auditTime}(度){0,1}(.*?)进行.*?审计/

        def matcher = context =~ industryRegex
        (0..<-matcher.count).each {
            auditIndustry = matcher[it][2]
            System.out.println("industry match : " + auditIndustry)
            System.out.println("num : " + num)
            num++
        }

        if(auditIndustry == null) {
            System.err.println("no match in getAuditIndustry method!")
        }

        return auditIndustry
    }

    def getAuditClassification(context) {
        return getAuditIndustry(context)
    }

}
