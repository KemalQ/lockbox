package lockBox;

class Hospital {

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
}