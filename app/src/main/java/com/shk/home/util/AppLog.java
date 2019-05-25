package com.shk.home.util;

import android.util.Log;

public class AppLog {
	private static boolean DEBUG_ENABLE = true;

	private static String DEF_TAG = "HomeDeskTop";

	private static void insertElement(StringBuilder sb, StackTraceElement element) {
		sb.append(element.getFileName()).append('-').append(element.getLineNumber()).append(':')
				.append(element.getMethodName()).append("()\n");
	}

	public static void print(Object... args) {
		if (args != null && args.length > 0 && args[0] instanceof Throwable) {
			Object[] temp = new Object[args.length - 1];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = args[i + 1];
			}

			prints((Throwable) args[0], 2, temp);
		} else {
			prints(null, 2, args);
		}
	}

	private static void prints(Throwable throwable, int trackIndex, Object... args) {
		if (!DEBUG_ENABLE) {
			return;
		}

		String tag = DEF_TAG;

		StringBuilder sb = new StringBuilder();

		StackTraceElement[] stack = new Throwable().getStackTrace();
		if (trackIndex >= 0 && trackIndex < stack.length) {
			insertElement(sb, stack[trackIndex]);
		} else {
			sb.append("-------------------------------\n");
			for (int i = stack.length - 1; i > 0; i--) {
				insertElement(sb, stack[i]);
			}
			sb.append("-------------------------------\n");
		}

		for (Object obj : args) {
			sb.append(String.valueOf(obj)).append(' ');
		}

		Log.d(tag, sb.toString());

		if (throwable != null) {
			Log.d(tag, "", throwable);
		}
	}
}
