package com.GameServer.Concept.QuizRoom;

import com.GameServer.Client;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RoomList {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private static final int MAXIMUM_ROOM = 400;

	private static RoomList singleton = null;

	private RoomList() {
	}

	public static RoomList getSingleton() {
		if (singleton == null) {
			singleton = new RoomList();
		}
		return singleton;
	}

	/**
	 * Stores all on-going rooms
	 */
	private ArrayList<Room> rooms = new ArrayList<>();

	/**
	 * 
	 * @param roomId
	 */
	public void destroyRoom(String roomId) {
		// TODO - implement RoomList.destroyRoom
		throw new UnsupportedOperationException();
	}

	public Room createRoom(Client admin, String quizId) {
		Room newRoom = new Room(String.valueOf(rooms.size()), admin, quizId);
		rooms.add(newRoom);
		logger.info(String.format("Room %s is created.", newRoom.getId()));
		return newRoom;
	}

	public Room findRoom(String id) {
		for (Room room: rooms) {
			logger.info(room.getId());
			if (room.getId().equals(id)) {
				return room;
			}
		}
		return null;
	}
}