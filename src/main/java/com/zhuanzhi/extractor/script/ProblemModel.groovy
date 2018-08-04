package com.zhuanzhi.extractor.script

import com.zhuanzhi.extractor.base.ModelExtractor
import com.zhuanzhi.structuring.model.Model
import com.zhuanzhi.structuring.model.Problem

import java.util.regex.Pattern

class ProblemModel extends ModelExtractor {

    private int num = 0

    def problemPosRegex = ~/^\（[^\d]{1,2}\）.*/

    Pattern problemRegex_1 = ~/\）(.*建，部.*)(，|。)/
    def problemRegex_2 = ~/\）(.*?)(\d|，|。)/


//    def lawRegex_1 = ~/根据(.*)的{0,1}?规定/
    def lawRegex_1 = ~/根据(.*)的规定/
    def lawRegex_2 = ~/违反了(.*)的{0,1}规定/
    def lawRegex_2_1 = ~/违反了(.*)”。/
    def lawRegex_3 = ~/不符合(.*)的{0,1}规定/
    def lawRegex_4 = ~/不符合(.*)的{0,1}要求/
    def lawRegex_5 = ~/依据(.*?)。/
//    def lawRegex_5 = ~/依据(.*)的{0,1}规定/

    @Override
    Model extractor(String context) {
        num = 0
        Problem problem = new Problem()
        problem.auditProblemsAndLaws = this.getAuditProblemsAndLaws(context)
        return problem
    }

    def getAuditProblemsAndLaws(context) {
//        System.out.println("problem : " + context.split("\n").size())

        ArrayList<HashMap<String, String>> auditProblemsAndLaws = new ArrayList<HashMap<String, String>>()

        def lines = context.split("\n")

        ArrayList<Integer> positions = new ArrayList<>()
        for(int i = 0; i < lines.size(); i++) {
            String line = lines[i]
            def matcher = line =~ problemPosRegex
            if(matcher.count > 0) {
                positions.add(i)
            }
        }
        positions.add(lines.size())

        String problem = null
        String laws = ""
        int begin = 0
        int end = 0
        for(int i = 0; i < positions.size() - 1; i++) {
            System.out.println("=======================" + (i + 1))
            begin = positions.get(i)
            end = positions.get(i + 1)

            problem = this.findProblem(lines[begin])

            for(int j = begin + 1; j < end; j++) {
                System.out.println("law postition :" + lines[j])
                String law = findLaw(lines[j])
                if (law != null) {
                    laws = laws + law
                }
            }

            HashMap<String, String> problemAndLaws = new HashMap<String, String>()
            if(laws.equalsIgnoreCase("")) {
                problemAndLaws.put(problem, null)
            }else {
                problemAndLaws.put(problem, laws)
            }
            auditProblemsAndLaws.add(problemAndLaws)
        }

        return auditProblemsAndLaws
    }

    def findProblem(line) {
        System.out.println(line)
        def l = line.split("，")[0]
        def markRegex = ~/\d{4}年至\d{4}年(.*)/
        def matcherMark = l =~ markRegex
        if (matcherMark.count > 0) {
            System.out.println(l)
            System.out.println("num : " + num)
            num++
            return l;
        }

        def matcherProblem_1 = line =~ problemRegex_1
        if (matcherProblem_1.count > 0) {
            def res = matcherProblem_1[0][1]
            System.out.println(res)
            System.out.println("num : " + num)
            num++
            return res;
        }

        def matcherProblem_2 = line =~ problemRegex_2
        if (matcherProblem_2.count > 0) {
            def res = matcherProblem_2[0][1]
            System.out.println(res)
            System.out.println("num : " + num)
            num++
            return res;
        }


    }

    def findLaw(line) {
        String law = "";

        def matcherLaw_1 = line =~ lawRegex_1
        if (matcherLaw_1.count > 0) {
            def res = matcherLaw_1[0][1]
            System.out.println(res)
            System.out.println("num : " + num)
            num++
            law = law  + res + "||"
        }

        def matcherLaw_2 = line =~ lawRegex_2
        if (matcherLaw_2.count > 0) {
            def res = matcherLaw_2[0][1]
            System.out.println(res)
            law = law  + res + "||"
            System.out.println("num : " + num)
            num++
        }

        def matcherLaw_2_1 = line =~ lawRegex_2_1
        if (matcherLaw_2_1.count > 0) {
            def res = matcherLaw_2_1[0]
            System.out.println(res)
            law = law  + res + "||"
            System.out.println("num : " + num)
            num++
        }

        def matcherLaw_3 = line =~ lawRegex_3
        if (matcherLaw_3.count > 0) {
            def res = matcherLaw_3[0]
            System.out.println(res)
            law = law  + res + "||"
            System.out.println("num : " + num)
            num++
        }

        def matcherLaw_4 = line =~ lawRegex_4
        if (matcherLaw_4.count > 0) {
            def res = matcherLaw_4[0]
            System.out.println(res)
            law = law  + res + "||"
            System.out.println("num : " + num)
            num++
        }

        def matcherLaw_5 = line =~ lawRegex_5
        if (matcherLaw_5.count > 0) {
            def res = matcherLaw_5[0][1]
            System.out.println(res)
            law = law  + res + "||"
            System.out.println("num : " + num)
            num++
        }

        return law
    }
}
