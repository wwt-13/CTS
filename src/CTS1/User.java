package CTS1;

import java.util.ArrayList;
import java.util.regex.Pattern;
import CTS1.exceptions.*;

public class User {
    private String name;
    private String sex;
    private String aadhaar;
    private enum ArgsType{//定义异常枚举类
        ARGUMENTS,NAME,SEX,AADHAAR,AADHAAR_NUMBER_EXIST,LEGAL
    }

    public User(){

    }
    public User(String name,String sex,String aadhear){
        this.name=name;
        this.sex=sex;
        this.aadhaar=aadhear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    //用于检查输入参数格式是否正确
    private ArgsType check(ArrayList<String> arg){
        if(arg.size()!=3){//参数数量不一致
            return ArgsType.ARGUMENTS;
        }
        else{//利用正则表达式判断格式
            String name=arg.get(0);
            String sex=arg.get(1);
            String aadhaar=arg.get(2);
            String regName="^[a-zA-Z_]+$";//名字格式
            String regSex="[MFO]";//性别格式
            String regAadhaar="(0(?!0{3})[0-9]{3}|1[0-1][0-9]{2}|12[0-2][0-9]|123[0-7])(00[2-9][0-9]|0[1-3][0-9]{2}|04[0-5][0-9]|0460)((0[0-9]{2}|100)[0-2])";//卡号匹配
            var patternName= Pattern.compile(regName);
            var patternSex=Pattern.compile(regSex);
            var patternAadhaar=Pattern.compile(regAadhaar);
            var matcherName=patternName.matcher(name);
            var matcherSex=patternSex.matcher(sex);
            var matcherAadhaar=patternAadhaar.matcher(aadhaar);
            if(!matcherName.find()){
                return ArgsType.NAME;
            }
            else if(!matcherSex.find()){
                return ArgsType.SEX;
            }
            else if(!matcherAadhaar.find()){
                return ArgsType.AADHAAR;
            }
            else if(UserDate.getInstance().exist(aadhaar)){//aadhaar已存在
                return ArgsType.AADHAAR_NUMBER_EXIST;
            }
            else{
                return ArgsType.LEGAL;//判断得到正常类型，可以直接向数据库中添加用户数据
            }
        }
    }

    public void addUser(ArrayList<String> arg){
        //利用正则表达式匹配上述情况
        var argType=check(arg);//判断参数类型
        switch(argType){
            case ARGUMENTS -> throw new ArgumentsIllegalException("Arguments illegal");
            case NAME -> throw new NameIllegalException("Name illegal");
            case SEX -> throw new SexIllegalException("Sex illegal");
            case AADHAAR -> throw new AadhaarIllegalException("Aadhaar number illegal");
            case AADHAAR_NUMBER_EXIST -> throw new AadhaarNumberExistIllegalException("Aadhaar number exist");
            case LEGAL -> {
                //可以直接将User信息加入数据库中了
                this.setName(arg.get(0));
                this.setSex(arg.get(1));
                this.setAadhaar(arg.get(2));
                UserDate.getInstance().add(this);//虽然有点离谱
            }
            default -> throw new IllegalStateException("Unexpected value: " + argType);//还出错的话，直接报告异常吧
        }
    }
    @Override
    public String toString(){
        return String.format("Name:%s\nSex:%s\nAadhaar:%s",this.name,this.sex,this.aadhaar);
    }
}
