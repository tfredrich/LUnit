package com.ecollege.lunit.summary;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecollege.lunit.event.EventCallback;
import com.ecollege.lunit.event.TestEvent;
import com.ecollege.lunit.event.TimerStopEvent;
import com.ecollege.lunit.repository.EventRepository;
import com.ecollege.lunit.scenario.Scenario;
import com.ecollege.lunit.test.TestCase;
import com.jigsforjava.util.Range;

/**
 * @author toddf
 * @since September 28, 2009
 */
public class BriefSummarizer
implements TestSummarizer
{
	private EventRepository repository;
	
	
	public EventRepository getRepository()
	{
		return repository;
	}

	public void setRepository(EventRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public List<TestSummary> summarize()
	{
		SummaryAccumulator accumulator = new SummaryAccumulator();
		repository.iterate(accumulator);
		return accumulator.getSummaries();
	}
	
	public class SummaryAccumulator
	implements EventCallback
	{
		private Map<Integer, TestSummary> summaries = new HashMap<Integer, TestSummary>();
		
		public List<TestSummary> getSummaries()
		{
			return new ArrayList<TestSummary>(summaries.values());
		}

		@Override
		public void handleEvent(TestEvent event)
		{
			accumulateInto(getSummaryFor(event), event);
		}
		
		private TestSummary getSummaryFor(TestEvent event)
		{
			Integer hashCode = event.getOriginator().hashCode();

			if (event.getOriginator() instanceof Scenario<?>)
			{
				Scenario<?> originator = ((Scenario<?>) event.getOriginator());
				hashCode = originator.getOwner().hashCode();
			}

			return getOrCreateSummary(hashCode);
		}

		private TestSummary getOrCreateSummary(Integer hashCode)
		{
			TestSummary result = summaries.get(hashCode);
			
			if (result == null)
			{
				result = new TestSummary();
				summaries.put(hashCode, result);
			}
			
			return result;
		}

		protected void accumulateInto(TestSummary summary, TestEvent event)
		{
			if (event.isTestStart())
			{
				summary.setTestName(((TestCase) event.getOriginator()).getName());
			}
			if (event.isError())
			{
				summary.setErrorCount(summary.getErrorCount() + 1);
			}
			else if (event.isFailure())
			{
				summary.setFailureCount(summary.getFailureCount() + 1);
			}
			else if (event.isActionStart())
			{
				summary.setActionCount(summary.getActionCount() + 1);
			}
			else if (event.isScenarioStart())
			{
				summary.setScenarioCount(summary.getScenarioCount() + 1);
			}
			else if (event.isActionStop())
			{
				TimerStopEvent timerStopEvent = (TimerStopEvent) event;
				Range<Date> timespan = timerStopEvent.getTimespan();
				long iterationTime = timespan.getEnd().getTime() - timespan.getStart().getTime();
				summary.setTotalActionTime(summary.getTotalActionTime() + iterationTime);
			}
			else if (event.isScenarioStop())
			{
				TimerStopEvent timerStopEvent = (TimerStopEvent) event;
				Range<Date> timespan = timerStopEvent.getTimespan();
				long elapsedTime = timespan.getEnd().getTime() - timespan.getStart().getTime();
				summary.setTotalScenarioTime(summary.getTotalScenarioTime() + elapsedTime);
			}
			else if (event.isTestStop())
			{
				TimerStopEvent timerStopEvent = (TimerStopEvent) event;
				Range<Date> timespan = timerStopEvent.getTimespan();
				long elapsedTime = timespan.getEnd().getTime() - timespan.getStart().getTime();
				summary.setElapsedTimeMillis(summary.getElapsedTimeMillis() + elapsedTime);
			}
		}
	}
}
