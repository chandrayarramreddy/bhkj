package com.mss.m1.api;


import org.springframework.stereotype.Service;


public interface M1Repository  {
	M1 findById(int id);
String findNameById(int id);
}
