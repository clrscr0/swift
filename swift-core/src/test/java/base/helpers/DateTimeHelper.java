package base.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import base.constants.BaseConfig;

public class DateTimeHelper {
	public static String getDateMonthFormat(String date) {
		DateFormat sdf = new SimpleDateFormat("MMM dd", Locale.US);
		String s1 = date;
		String s2 = null;
		Date d;
		try {
			d = sdf.parse(s1);
			s2 = (new SimpleDateFormat("M/d")).format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return s2;
	}

	public static Date stringToDate(String format, String date)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	public static String getCurrentDateTime(String dateTimeFormat) {
		DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	public static long getTimeDifference(String startTime, String endTime)
			throws ParseException {
		Date start = new SimpleDateFormat(BaseConfig.DATETIME_FORMAT)
				.parse(startTime);
		Date end = new SimpleDateFormat(BaseConfig.DATETIME_FORMAT)
				.parse(endTime);
		return end.getTime() - start.getTime();
	}

	public static String formatTime(long timeDifference) {
		return String.format(
				"%d min(s), %d sec(s)",
				TimeUnit.MILLISECONDS.toMinutes(timeDifference),
				TimeUnit.MILLISECONDS.toSeconds(timeDifference)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(timeDifference)));
	}
}
