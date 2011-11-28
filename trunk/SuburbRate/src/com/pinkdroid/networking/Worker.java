package com.pinkdroid.networking;

public interface Worker<Params, Result> {

	public Result heavyTask(Params ... params) throws Exception;
}
