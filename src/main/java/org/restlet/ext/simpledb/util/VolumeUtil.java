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

	public static List<Props> selectItems(final AmazonSimpleDB client,
			final Volume volume, final String select) throws Exception {

		final Collection<Callable<List<Props>>> taskList = new LinkedList<Callable<List<Props>>>();

		for (int index = 0; index < volume.getDomainCount(); index++) {

			final int domainIndex = index;

			final Callable<List<Props>> task = new Callable<List<Props>>() {
				@Override
				public List<Props> call() throws Exception {

					final String domain = volume.getDomainName(domainIndex);

					final List<Item> itemList = SimpleUtil//
							.selectItems(client, domain, select);

					final List<Props> jsonList = new LinkedList<Props>();

					for (final Item item : itemList) {
						Props props = SimpleUtil.getProps(item);
						jsonList.add(props);
					}

					return jsonList;
				}
			};

			taskList.add(task);

		}

		final List<Future<List<Props>>> futureList = getExecutor().invokeAll(
				taskList);

		final List<Props> jsonList = new LinkedList<Props>();

		for (final Future<List<Props>> future : futureList) {
			final List<Props> list = future.get();
			jsonList.addAll(list);

		}

		return jsonList;

	}

}
