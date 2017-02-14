package bean;

import domain.train.TrainEvaResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TrainTempData implements Serializable {

	// 当前步骤 <norms.index>
	public int stopStep;
	// 总步骤 <normNum>
	public int stepNum;
	// 已保存结果 <normId, TrainEvaResult>
	public Map<Integer, TrainEvaResult> trainEvaResultMap;
	// 评估意见
	public String feedback;
	// 评估总分
	public Integer score;

	public TrainTempData() {
		
		trainEvaResultMap = new HashMap<Integer, TrainEvaResult>();
	}

	public int getStopStep() {
		return stopStep;
	}

	public void setStopStep(int stopStep) {
		this.stopStep = stopStep;
	}

	public int getStepNum() {
		return stepNum;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

	public Map<Integer, TrainEvaResult> getTrainEvaResultMap() {
		return trainEvaResultMap;
	}

	public void setTrainEvaResultMap(Map<Integer, TrainEvaResult> trainEvaResultMap) {
		this.trainEvaResultMap = trainEvaResultMap;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
