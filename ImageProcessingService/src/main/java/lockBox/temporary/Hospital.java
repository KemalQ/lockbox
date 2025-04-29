package lockBox.temporary;

class Hospital {
    private static int doctors;
    public final static int NURSES = 10;
    protected double salaries;

    private Hospital() {
    }

    public Hospital(int doctors, int nurses) {
        this.doctors = doctors;
        //this.NURSES = nurses;
    }

    public Hospital(int doctors) {
        this.doctors = doctors;
    }

    public Hospital(double salaries) {
        this.salaries = salaries;
    }


    public String calculate(String input) {
        String[] data = input.split(" ");
        double per=0;
        double sq=0;

        for(int k=0; k<data.length; k++/*String i : data*/){
            if(data[k].equals("круг")){
                int num = Integer.parseInt(data[k]);
                if(data[k+1] != null){

                    sq=num*num*3.14;
                    per=2*3.14*num;
                }
                else{ sq=1*1*3.14;}
            }
            if(data[k].equals("квадрат")){
                if(data[k+1] != null){
                    int num = Integer.parseInt(data[k]);
                    sq=num*num;
                }
                else{ sq=4;
                }
            }
        }
        return ""+sq + " " + per;
    }

    public String calculateStats(String input) {
        String[] text = input.split(" ");
        int upper=0;
        int lower=0;
        int zero=0;
        for(int i=0; i<text.length; i++){
            if(Integer.parseInt(text[i])>0) upper++;
            else if(Integer.parseInt(text[i])<0) lower++;
            else zero++;
        }
        return "выше нуля: "+upper+", ниже нуля: "+lower+", равна нулю: "+zero;
    }
}