package bigframe.qgen.engineDriver;

import bigframe.bigif.WorkflowInputFormat;


/**
 * A abstract class to control the workflow running on a possible system.
 * 
 * @author andy
 *
 */
public abstract class EngineDriver {
    protected WorkflowInputFormat workIF;
	
	public EngineDriver(WorkflowInputFormat workIF) {
		this.workIF = workIF;
	}
	
	public abstract int numOfQueries();
	
	public abstract void init();
	
	public abstract void run();
	
	public abstract void cleanup();
}
