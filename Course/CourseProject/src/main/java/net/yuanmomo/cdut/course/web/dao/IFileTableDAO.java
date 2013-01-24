package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.FileTable;

public interface IFileTableDAO {
	//insert a file
	public boolean doInsert(FileTable file);
	//update a file
	public boolean doUpdate(FileTable file);
	//delete a file
	public boolean doDelete(int id);
	//get files by role
	public List<FileTable> getFilesByRole(int currentPage,int pageSize,int role);
	//get files count by role
	public int getFilesCountByRole(int role);
	//get all files
	public List<FileTable> getAllFiles(int currentPage,int pageSize);
	//get all files count
	public int getAllFilesCount();
	//get file by fileName
	public FileTable getFileByFileName(String fileName);
	//get file by fileId
	public FileTable getFileByFileId(int id);
}
