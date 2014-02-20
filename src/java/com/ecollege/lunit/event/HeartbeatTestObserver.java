/**
 * 
 */
package com.ecollege.lunit.event;

/**
 * Simply displays a character on the console every actionCount actions to show that
 * things are still happening in a long-running, otherwise silent test.
 * 
 * @author toddf
 * @since October 1, 2009
 */
public class HeartbeatTestObserver
implements TestObserver
{
	private int actionCount = 100;
	private String token = ".";
	private int actionsStarted = 0;

	public HeartbeatTestObserver()
	{
		super();
	}

	public HeartbeatTestObserver(int actionCount, String token)
	{
		this();
		setActionCount(actionCount);
		setToken(token);
	}

	public int getActionCount()
	{
		return actionCount;
	}

	public void setActionCount(int actionCount)
	{
		this.actionCount = actionCount;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	@Override
	public void notify(TestEvent event)
	{
		if (event.isActionStart())
		{
			if (actionsStarted == 0)
			{
				System.out.print(token);
			}

			if (actionsStarted >= actionCount)
			{
				actionsStarted = 0;
			}
			else
			{
				++actionsStarted;
			}
		}
	}
}
