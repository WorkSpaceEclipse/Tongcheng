package com.android.tools;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;

public class Rong {

	public static void sendImg(InputStream is, String targetId, String content) {
		File imageFileSource = new File(
				Const.FILEPATH_IMG, "source.jpg");
		File imageFileThumb = new File(
				Const.FILEPATH_IMG, "thumb.jpg");

		try {
			// 读取图片。
			Bitmap bmpSource = BitmapFactory.decodeStream(is);

			imageFileSource.createNewFile();

			FileOutputStream fosSource = new FileOutputStream(imageFileSource);

			// 保存原图。
			bmpSource.compress(Bitmap.CompressFormat.JPEG, 100, fosSource);

			// 创建缩略图变换矩阵。
			Matrix m = new Matrix();
			m.setRectToRect(
					new RectF(0, 0, bmpSource.getWidth(), bmpSource.getHeight()),
					new RectF(0, 0, 160, 160), Matrix.ScaleToFit.CENTER);

			// 生成缩略图。
			Bitmap bmpThumb = Bitmap.createBitmap(bmpSource, 0, 0,
					bmpSource.getWidth(), bmpSource.getHeight(), m, true);
			
			imageFileThumb.createNewFile();

			FileOutputStream fosThumb = new FileOutputStream(imageFileThumb);

			// 保存缩略图。
			bmpThumb.compress(Bitmap.CompressFormat.JPEG, 90, fosThumb);

		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageMessage imgMsg = ImageMessage.obtain(Uri.fromFile(imageFileThumb),
				Uri.fromFile(imageFileSource));

		/**
		 * 发送图片消息。
		 * 
		 * @param type
		 *            会话类型。
		 * @param targetId
		 *            目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室
		 *            Id。
		 * @param content
		 *            消息内容。
		 * @param callback
		 *            发送消息的回调。
		 */

		RongIM.getInstance()
				.getRongIMClient()
				.sendImageMessage(Conversation.ConversationType.PRIVATE,
						targetId, imgMsg, " ", " ",
						new RongIMClient.SendImageMessageCallback() {

							@Override
							public void onAttached(Message message) {
								// 保存数据库成功
							}

							@Override
							public void onError(Message message,
									RongIMClient.ErrorCode code) {
								// 发送失败
							}

							@Override
							public void onSuccess(Message message) {
								// 发送成功
							}

							@Override
							public void onProgress(Message message, int progress) {
								// 发送进度
							}
						});

	}

	public static void sendMessage(String userId, String title, String content,
			String imgUrl) {

		RichContentMessage richContentMessage = RichContentMessage.obtain(
				title, content, imgUrl);

		/**
		 * 发送消息。
		 * 
		 * @param type
		 *            会话类型。
		 * @param targetId
		 *            目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室
		 *            Id。
		 * @param content
		 *            消息内容。
		 * @param pushContent
		 *            push 时提示内容。
		 * @param callback
		 *            发送消息的回调。
		 * @return
		 */
		RongIM.getInstance()
				.getRongIMClient()
				.sendMessage(Conversation.ConversationType.PRIVATE, userId,
						richContentMessage, "", imgUrl,
						new RongIMClient.SendMessageCallback() {
							@Override
							public void onError(Integer messageId,
									RongIMClient.ErrorCode e) {

							}

							@Override
							public void onSuccess(Integer integer) {

							}
						});

	}
}
