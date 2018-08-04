package com.zhuanzhi.extractor.script

import com.zhuanzhi.extractor.base.ModelExtractor
import com.zhuanzhi.structuring.model.Model
import com.zhuanzhi.structuring.model.Underlying

class UnderlyingModel extends ModelExtractor{
    @Override
    Model extractor(String context) {
        Underlying underlying = getData(context)
        underlying.auditName = getAuditName(context)
        return underlying
       /* underlying.budgetUnit = this.getBudgetUnit(context)
        underlying.theTotalNumberOfPeople = this.getTheTotalNumberofPeople(context)
        underlying.Administrate = this.getAdministrate(context)
        underlying.fullSubsidyCareeaStaffing = this.getFullSubsidyCareeaStaffing(context)
        underlying.theActualNumber = this.getTheActualNumer(context)
        underlying.functionsDepartment = this.getFunctionsDepartment(context)
        underlying.fd_Administrate = this.getFd_Administrate(context)
        underlying.fd_Careea = this.getFd_Careea(context)
        underlying.fd_theActualNumber = this.getFd_TheActuallNumbern(context)*/
       // return underlying
    }

    def auditNameRedex = ~/(.*)共有/
    def Regex1 = ~/((\d\d)人|(\d)个)/
    def Regex2 = ~/(\d)/
    def Regex3 = ~/(\d)/

    def getAuditName(context) {
       // System.out.println(context)
        def lines= context.split("\n")
        for (int i=0;i<lines.size();i++){
            def line = lines[i]
            def matcherName = line =~ auditNameRedex
            if (matcherName.getCount()>0){
                def result = matcherName[0][1]
               // System.out.println(result)
                return result
            }
    }

    }

    def getData(context){
        Underlying underlying  = new Underlying()
        def lines= context.split("，")
         def count=0;
        for (int i=0;i<lines.size();i++){
           // System.out.println(lines[i])
            def line = lines[i].split("。")
            for (int j=0;j<line.size();j++) {
              //  System.out.println(count+1+line[j])
                count++
                //System.out.println(count)
                switch (count){
                    case 1: underlying.budgetUnit=getBudgetUnit(line[j])
                       // System.out.println(underlying.budgetUnit)
                        break
                    case 2:underlying.theTotalNumberOfPeople=getTheTotalNumberofPeople(line[j])
                        //System.out.println(underlying.theTotalNumberOfPeople)
                        break
                    case 3:underlying.Administrate=getAdministrate(line[j])
                      //  System.out.println(underlying.Administrate)
                        break
                    case 4:underlying.fullSubsidyCareeaStaffing=getFullSubsidyCareeaStaffing(line[j])
                      //  System.out.println(underlying.fullSubsidyCareeaStaffing)
                        break
                    case 5:underlying.theActualNumber=getTheActualNumer(line[j])
                       // System.out.println(underlying.theActualNumber)
                        break
                    case 6:underlying.functionsDepartment=getFunctionsDepartment(line[j])
                       // System.out.println(underlying.functionsDepartment)
                        break
                    case 7:underlying.fd_Administrate=getFd_Administrate(line[j])
                      //  System.out.println(underlying.fd_Administrate)
                        break
                    case 8:underlying.fd_Careea=getFd_Careea(line[j])
                       // System.out.println(underlying.fd_Careea)
                        break
                    case 9:underlying.fd_theActualNumber=getFd_TheActuallNumbern(line[j])
                       // System.out.println(underlying.fd_theActualNumber)
                        break
            }
            }
        }
        return underlying
    }

    def getBudgetUnit(context)
    {
        def matcherName = context =~ Regex3
        if (matcherName.getCount()>0) {
            def result = matcherName[0][1]
            //System.out.println(result)
            return result
        }
    }

    def getTheTotalNumberofPeople(context){

        def matcherName = context =~ Regex1
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
          //  System.out.println(result)
            return result
        }

    }

    def getAdministrate(context){
        def matcherName = context =~ Regex1
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
          //  System.out.println(result)
            return result
        }

    }

    def getFullSubsidyCareeaStaffing(context){
        def matcherName = context =~ Regex1
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
         //   System.out.println(result)
            return result
        }

    }

    def getTheActualNumer(context){
        def matcherName = context =~ Regex1
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
          //  System.out.println(result)
            return result
        }

    }

    def getFunctionsDepartment(context){
        def matcherName = context =~ Regex3
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
         //   System.out.println(result)
            return result
        }

    }

    def getFd_Administrate(context){
        def matcherName = context =~ Regex1
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
           // System.out.println(result)
            return result
        }

    }

    def getFd_Careea(context){
        def matcherName = context =~ Regex2
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
           // System.out.println(result)
            return result
        }

    }
    def getFd_TheActuallNumbern(context){
        def matcherName = context =~ Regex1
        if (matcherName.getCount()>0){
            def result = matcherName[0][1]
           // System.out.println(result)
            return result
        }

    }
}
