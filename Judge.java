public class Judge {
    private int[][] matrix; //this is the adjacency matrix that is constructed from the relationships within the trust

    //finds the judge by constructing a directional graph that is represented by an adjacency matrix
    /*
        The judge can be found in the adjacency matrix by identifying the index of the person who has 0's
        all across its row and 1's all across its column (except for the position where the row and column are the same -- that must be a 0)
    */
    public int findJudge(int N, int[][] trust) throws Exception {
        this.constructAdjacencyMatrix(N, trust);
        for(int i = 0; i < this.matrix.length; i++) {
            int[] arr = this.matrix[i];
            //if this person is trusted by every other person AND this person does not trust anyone, then this person is a judge
            if(this.isJudge(i) && this.isJudge(arr)) {
                return i+1; /*
                                i+1 is returned because the person is uniquely identified by a number between 1 to N, and the indicies of the matrix
                                go from 0 to N-1, so +1 must be added to return the proper person
                            */
            }
        }
        //no judge was found
        return -1;
    }

    //determines if a person is trusted by every other person 
    private boolean isJudge(int person) {
        for(int i = 0; i < this.matrix.length; i++) {
            int[] arr = this.matrix[i];
            //if this person is not himself/herself and this person is not trusted by another person, then this person cannot be a judge
            if(i != person && arr[person] == 0) {
                return false;
            }
        }
        //this person is trusted by every other person, then this person may be a judge 
        return true;
    }

    //determines if a person does not trust every other person
    /*
        The parameter, int[] arr, represents the trust relationship between a singular person and every other person.
        If all values within int[] arr are 0, then this person does not trust anyone, and therefore may be a judge
    */
    private boolean isJudge(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            //if arr[i] != 0, then that indicates that this person trusts someone, so this person cannot be a judge 
            if(arr[i] != 0) {
                return false;
            }
        }
        //this person does not trust anyone, so this person may be a judge 
        return true;
    }

    //prints the adjacency matrix that was constructed to get a visual representation of all the trust relationships between each person 
    public void printAdjacencyMatrix() {
        String lineBreak = "";
        for(int i = 0; i < this.matrix[0].length; i++) {
            if(i == 0) {
                lineBreak = lineBreak + "- ";
                continue;
            }
            lineBreak = lineBreak + " - ";
        }
        for(int i = 0; i < this.matrix.length; i++) {
            int[] arr = this.matrix[i];
            String row = "";
            for(int j = 0; j < arr.length; j++) {
                if(j == 0) {
                    row = row + arr[j] + " ";
                    continue;
                }
                row = row + " " + arr[j] + " ";
            }
            System.out.println(row);
            System.out.println(lineBreak);
        }
    }

    //constructs adjacency matrix from the trust 
    private void constructAdjacencyMatrix(int N, int[][] trust) throws Exception {
        int numPeople = this.getNumOfPeople(trust);
        //checks to see if the parameter, int N, reflects the amount of unique people within trust
        if(numPeople != N) {
            throw new Exception("Number of people specified does not reflect in the trust matrix");
        }
        this.matrix = new int[N][N];
        for(int i = 0; i < trust.length; i++) {
            int[] arr = trust[i];
            for(int j = 0; j < arr.length; j++) {
                int person1 = arr[0] - 1; //subtract by 1 because the rubric seemed to indicate that all persons are values from 1 to N (there cannot be a person denoted with 0)
                int person2 = arr[1] - 1; //subtract by 1 because the rubric seemed to indicate that all persons are values from 1 to N (there cannot be a person denoted with 0)
                this.matrix[person1][person2] = 1; //
            }
        }
    }

    //counts the number of unique persons in the trust (a person is denoted by any number from 1 to N)
    private int getNumOfPeople(int[][] trust) {
        int max = 0;
        for(int i = 0; i < trust.length; i++) {
            int[] arr = trust[i];
            for(int j = 0; j < arr.length; j++) {
                if(arr[0] > max) {
                    max = arr[0];
                }
                if(arr[1] > max) {
                    max = arr[1];
                }
            }
        }
        return max;
    }

    public static void main(String[] args) throws Exception {
        //int[][] trust = {{1,3}, {1,4}, {2,3}, {2,4}, {4,3}};
        int[][] trust = {{1,3}, {2,3}, {3,1}};
        Judge j = new Judge();
        int judge = j.findJudge(3, trust);
        j.printAdjacencyMatrix();
        System.out.println("JUDGE: " + judge);
    }
}
