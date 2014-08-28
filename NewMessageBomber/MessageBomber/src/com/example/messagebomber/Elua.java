package com.example.messagebomber;

import java.io.*;

import com.example.messagebomber.R;

import android.app.*;
import android.content.*;

public class Elua {
	private static final String ASSERT_EULA = "Protocal";
	private static final String PREFERENCE_EULA_ACCEPTED = "eula.accepted";
	private static final String PREFERENCE_EULA = "eula";

	/**
	 * 当用户选择同意后的回调函数。
	 */
	public static interface OnEulaAgreedTo {
		void onEulaAgreedTo();
	}

	private static void accept(SharedPreferences preferences) {
		preferences.edit().putBoolean(PREFERENCE_EULA_ACCEPTED, true).commit();
	}

	private static void refuse(Activity activity) {
		activity.finish();
	}

	private static void closeStream(Closeable stream) {
		if (null != stream) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static CharSequence readEula(Activity activity) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(activity.getAssets()
					.open(ASSERT_EULA)));
			String line;
			StringBuilder buffer = new StringBuilder();
			while ((line = in.readLine()) != null) {
				buffer.append(line).append("\n");
			}
			return buffer;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			closeStream(in);
		}
	}

	static boolean show(final Activity activity) {
		final SharedPreferences preferences = activity.getSharedPreferences(
				PREFERENCE_EULA, Activity.MODE_PRIVATE);

		if (!preferences.getBoolean(PREFERENCE_EULA_ACCEPTED, false)) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					activity);
			builder.setTitle(R.string.eula_title);
			builder.setCancelable(true);
			builder.setPositiveButton(R.string.eula_accept,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							accept(preferences);
							if (activity instanceof OnEulaAgreedTo) {
								((OnEulaAgreedTo) activity).onEulaAgreedTo();
							}
						}
					});
			builder.setNegativeButton(R.string.eula_refuse,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							refuse(activity);
						}
					});
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					refuse(activity);
				}
			});
			builder.setMessage(readEula(activity));
			builder.create().show();
			return false;
		}
		return true;
	}
}