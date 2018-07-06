class Score {
	private int score;
	
	Score (int score){
		this.score = score;
	}
	
	int getScore(){
		return score;
	}
	
	void add (int points){
		score += points;
	}
}
