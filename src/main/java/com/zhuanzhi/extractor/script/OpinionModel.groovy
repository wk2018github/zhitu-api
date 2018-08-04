package com.zhuanzhi.extractor.script

import com.zhuanzhi.extractor.base.ModelExtractor
import com.zhuanzhi.structuring.model.Model
import com.zhuanzhi.structuring.model.Opinion


class OpinionModel extends ModelExtractor {
    @Override
    Model extractor(String context) {
        Opinion opinion = new Opinion()

        opinion.auditTarget = getAuditReporter(context)
        opinion.auditCoverage = FindauditCoverage(context)
        opinion.auditTotalFunds = FindauditTotalFunds(context) + "万元"
        opinion.advice = FindAdvices1(context)

        return opinion
    }

    def auditTargetRegex1 = ~/，(.*)能按照/
    def auditTargetRegex2 = ~/，(.*)重视工伤/
    def auditTargetRegex3 = ~/审计结果表明(.*)2012年度/

    def auditCoverageRegex = ~/审计覆盖面(.*?)，/

    def auditTotalFundsRegex1 = ~/审计资金总额达到(.*)万元/
    def auditTotalFundsRegex2 = ~/少征金额(.*?)万元/
    def auditTotalFundsRegex3 = ~/挪用易地建设费(.*?)万元/

    def adviceRegex1 = ~/审计结果表明，(.*管理规定)/
    def adviceRegex2 = ~/发现，(.*?)，/
    def adviceRegex8 = ~/建设费问题，(.*)205个/
    def adviceRegex4 = ~/205个，(.*)15638万元/
    def adviceRegex5 = ~/15638万元，(.*)53%/
    def adviceRegex6 = ~/53%。(.*)13978万元/
    def adviceRegex7 = ~/13978万元等问题，(.*纠正)/

    def adviceRegex2_1 = ~/审计结果表明(.*?)，会计处理/
    def adviceRegex2_2 = ~/法律法规，(.*?)。/
    def adviceRegex2_3 = ~/制度规定。(.*?)。/

    def adviceRegex3 = ~/，(.*?)。/
    def adviceRegex3_1 = ~/审计结果表明，(.*?)。建立/
    def adviceRegex3_2 = ~/规章制度。(.*?)。/
    def adviceRegex3_3 = ~/保障作用。(.*?)。/
    def adviceRegex3_4 = ~/会计制度。(.*?)。/

    ArrayList<String> advice = new ArrayList<String>()
    def getAuditReporter(context) {


        def lines = context.split("\n")
        for (int i = 0; i < lines.size(); i++) {
            def line = lines[i]
            FindAuditTarget(line)
            FindauditCoverage(line)
            FindauditTotalFunds(line)
            FindAdvices1(line)
            //return advice

        }

    }

    def FindAuditTarget(line) {
        def matcherTarget1 = line =~ auditTargetRegex1
        if (matcherTarget1.getCount() > 0) {
            def reslution = matcherTarget1[0][1]
            //System.out.println(reslution)

            return reslution;
        }

        def matcherTarget2 = line =~ auditTargetRegex2
        if (matcherTarget2.getCount() > 0) {
            def reslution = matcherTarget2[0][1]
            //System.out.println(reslution)

            return reslution;
        }

        def matcherTarget3 = line =~ auditTargetRegex3
        if (matcherTarget3.getCount() > 0) {
            def reslution = matcherTarget3[0][1]
            // System.out.println(reslution)

            return reslution;
        }
    }

    def FindauditCoverage(line) {
        def matcherCoverage = line =~ auditCoverageRegex
        if (matcherCoverage.getCount() > 0) {
            def resulation = matcherCoverage[0][1]
            System.out.println(resulation)
            return resulation
        } else {
            // System.out.println(resulation)
            return null
        }
    }

    def FindauditTotalFunds(line) {
        def totalFund = 0
        def matcherTotalFunds1 = line =~ auditTotalFundsRegex1
        if (matcherTotalFunds1.getCount() > 0) {
            def result = matcherTotalFunds1[0][1]
            System.out.println(result)

            return result
        }
        def matcherTotalFunds2 = line =~ auditTotalFundsRegex2
        if (matcherTotalFunds2.getCount() > 0) {
            def result = matcherTotalFunds2[0][1]
            //System.out.println(result)
            totalFund += Integer.parseInt(result)
            //System.out.println(totalFund)
        }

        def matcherTotalFunds3 = line =~ auditTotalFundsRegex3
        if (matcherTotalFunds3.getCount() > 0) {
            def result = matcherTotalFunds3[0][1]
            //System.out.println(result)
            totalFund += Integer.parseInt(result)
            System.out.println(totalFund)
            return totalFund

        }
        return totalFund

    }

    def FindAdvices1(line) {
        def matcherAdvice1 = line =~ adviceRegex1
        if (matcherAdvice1.getCount() > 0) {
            def result = matcherAdvice1[0][1]
            advice.add(result)
           // System.out.println(result)
        }

        def matcherAdvice2 = line =~ adviceRegex2
        if (matcherAdvice2.getCount() > 0) {
            def result = matcherAdvice2[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice3 = line =~ adviceRegex8
        if (matcherAdvice3.getCount() > 0) {
            def result = matcherAdvice3[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice4 = line =~ adviceRegex4
        if (matcherAdvice4.getCount() > 0) {
            def result = matcherAdvice4[0][1]
            advice.add(result)
           // System.out.println(result)
        }

        def matcherAdvice5 = line =~ adviceRegex5
        if (matcherAdvice5.getCount() > 0) {
            def result = matcherAdvice5[0][1]
            advice.add(result)
            //System.out.println(result)

        }
        def matcherAdvice6 = line =~ adviceRegex6
        if (matcherAdvice6.getCount() > 0) {
            def result = matcherAdvice6[0][1]
            advice.add(result)
           // System.out.println(result)
        }

        def matcherAdvice7 = line =~ adviceRegex7
        if (matcherAdvice7.getCount() > 0) {
            def result = matcherAdvice7[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice2_1 = line =~ adviceRegex2_1
        if (matcherAdvice2_1.getCount() > 0) {
            def result = matcherAdvice2_1[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice2_2 = line =~ adviceRegex2_2
        if (matcherAdvice2_2.getCount() > 0) {
            def result = matcherAdvice2_2[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice2_3 = line =~ adviceRegex2_3
        if (matcherAdvice2_3.getCount() > 0) {
            def result = matcherAdvice2_3[0][1]
            advice.add(result)
           // System.out.println(result)
        }

        def matcherAdvice3_1= line =~ adviceRegex3_1
        if (matcherAdvice3_1.getCount() > 0) {
           def result = matcherAdvice3_1[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice3_2= line =~ adviceRegex3_2
        if (matcherAdvice3_2.getCount() > 0) {
            def result = matcherAdvice3_2[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice3_3= line =~ adviceRegex3_3
        if (matcherAdvice3_3.getCount() > 0) {
            def result = matcherAdvice3_3[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        def matcherAdvice3_4= line =~ adviceRegex3_4
        if (matcherAdvice3_4.getCount() > 0) {
            def result = matcherAdvice3_4[0][1]
            advice.add(result)
            //System.out.println(result)
        }

        return advice
        }


  /*  def FindAdvices2(line)
    {
        System.out.println("**************************")
        def matcherAdvice3_1= line =~ adviceRegex3
        if (matcherAdvice3_1.getCount() > 0) {
//            def result = matcherAdvice3_1[0][1]
//            //opinion.advice.put(result)
//            System.out.println(result)
            (0..<-matcherAdvice3_1.count).each {
                System.out.println(matcherAdvice3_1[it][1])
            }

//        (0..<-matcherAdvice3_1.count).each {
//            System.out.println(matcherAdvice3_1[it][1])
        }
    }*/
}
