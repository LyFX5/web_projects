package test;

abstract class Essence {
    private int space;

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Essence essence = (Essence)obj;
        if(space != essence.space) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + space;
        return result;
    }

    @Override
    public String toString() {
        return "Essence{"+
                "space="+space+"}";
    }
}
