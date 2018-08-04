package com.zhuanzhi.extractor.script

import com.zhuanzhi.extractor.base.ModelExtractor
import com.zhuanzhi.structuring.model.Advice
import com.zhuanzhi.structuring.model.Model

class AdviceModel extends ModelExtractor{
    @Override
    Model extractor(String context) {
        Advice advice = new Advice()
        advice.advices = getAdvice(context)
        return advice
    }

    def adviceRegex1 = ~/\）(.*)。/
    def adviceRegex2 = ~/(.*)。/

    def getAdvice(context) {
        ArrayList<String> advices = new ArrayList<>()
        def lines = context.split("\n")
        for (int i=0;i < lines.size();i++){
            String line =lines[i]
            //System.out.println(i+line)
            def advice = this.findAdvices(line)
            if(advice != null) {
                advices.add(advice)
            }

        }
        return advices

    }

    def findAdvices(line){
        def matcherAdvice1 = line =~ adviceRegex1
        if (matcherAdvice1.count>0){
            def reslution = matcherAdvice1[0][1]
            System.out.println(reslution)

            return reslution;
        }

        def matcherAdvice2 = line =~ adviceRegex2
        if (matcherAdvice2.count>0){
            def reslution = matcherAdvice2[0][1]
            System.out.println(reslution)

            return reslution;
        }
    }
}
