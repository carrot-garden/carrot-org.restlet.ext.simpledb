package org.restlet.ext.simpledb.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import org.restlet.ext.simpledb.api.Volume;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Item;

public class VolumeUtil {

	public static void makeDomains(AmazonSimpleDB client, Volume volume)
			throws Exception {

		for (int index = 0; index < volume.getDomainCount(); index++) {
			String domain = volume.getDomainName(index);
			SimpleUtil.makeDomain(client, domain);
		}

	}

	private static volatile ExecutorService executor;

	private static ExecutorService getExecutor() {

		if (executor == null) {

			ThreadFactory factory = new ThreadFactory() {
				@Override
				public Thread newThread(Runnable task) {
					Thread thread = new Thread(task, "# aws simpledb pool");
					thread.setDaemon(true);
					return thread;
				}

			};

			executor = Executors.newCachedThreadPool(factory);

		}

		return executor;

	}

	public static List<String> selectItems(final AmazonSimpleDB client,
			final Volume volume, final String select) throws Exception {

		final Collection<Callable<List<String>>> taskList = new LinkedList<Callable<List<String>>>();

		for (int index = 0; index < volume.getDomainCount(); index++) {

			final int domainIndex = index;

			final Callable<List<String>> task = new Callable<List<String>>() {
				@Override
				public List<String> call() throws Exception {

					String domain = volume.getDomainName(domainIndex);

					List<Item> itemList = SimpleUtil.selectItems(client,
							domain, select);

					List<String> jsonList = new LinkedList<String>();

					for (Item item : itemList) {
						String json = SimpleUtil.getJson(item);
						jsonList.add(json);
					}

					return jsonList;
				}
			};

			taskList.add(task);

		}

		final List<Future<List<String>>> futureList = getExecutor().invokeAll(
				taskList);

		final List<String> jsonList = new LinkedList<String>();

		for (Future<List<String>> future : futureList) {
			List<String> list = future.get();
			jsonList.addAll(list);

		}

		return jsonList;

	}

}
