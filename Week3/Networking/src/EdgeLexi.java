public class EdgeLexi implements Comparable<EdgeLexi> {
    int source;
    int target;
    int weight;

    public EdgeLexi(int source, int target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public int compareTo(EdgeLexi compareEdge) {
        if(this.source == compareEdge.source){
            return this.target - compareEdge.target;
        }
        return this.source - compareEdge.source;
    }
}
