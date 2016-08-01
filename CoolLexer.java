/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int comentario = 0;
    int get_curr_lineno() {
	return curr_lineno;
    }
    private AbstractSymbol filename;
    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	return filename;
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int ESTOFERROR = 3;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int STR = 4;
	private final int STRERROR = 5;
	private final int COMMENTLINEAL = 2;
	private final int yy_state_dtrans[] = {
		0,
		251,
		252,
		253,
		254,
		255
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NOT_ACCEPT,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NOT_ACCEPT,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NOT_ACCEPT,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NOT_ACCEPT,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NOT_ACCEPT,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NOT_ACCEPT,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NOT_ACCEPT,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NOT_ACCEPT,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NOT_ACCEPT,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NOT_ACCEPT,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NOT_ACCEPT,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NOT_ACCEPT,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NOT_ACCEPT,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NOT_ACCEPT,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NOT_ACCEPT,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NOT_ACCEPT,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NOT_ACCEPT,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NOT_ACCEPT,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NOT_ACCEPT,
		/* 202 */ YY_NO_ANCHOR,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NOT_ACCEPT,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NOT_ACCEPT,
		/* 208 */ YY_NO_ANCHOR,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NOT_ACCEPT,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NO_ANCHOR,
		/* 213 */ YY_NOT_ACCEPT,
		/* 214 */ YY_NO_ANCHOR,
		/* 215 */ YY_NO_ANCHOR,
		/* 216 */ YY_NOT_ACCEPT,
		/* 217 */ YY_NO_ANCHOR,
		/* 218 */ YY_NO_ANCHOR,
		/* 219 */ YY_NOT_ACCEPT,
		/* 220 */ YY_NO_ANCHOR,
		/* 221 */ YY_NO_ANCHOR,
		/* 222 */ YY_NOT_ACCEPT,
		/* 223 */ YY_NO_ANCHOR,
		/* 224 */ YY_NO_ANCHOR,
		/* 225 */ YY_NOT_ACCEPT,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NO_ANCHOR,
		/* 228 */ YY_NOT_ACCEPT,
		/* 229 */ YY_NO_ANCHOR,
		/* 230 */ YY_NOT_ACCEPT,
		/* 231 */ YY_NO_ANCHOR,
		/* 232 */ YY_NOT_ACCEPT,
		/* 233 */ YY_NO_ANCHOR,
		/* 234 */ YY_NOT_ACCEPT,
		/* 235 */ YY_NO_ANCHOR,
		/* 236 */ YY_NOT_ACCEPT,
		/* 237 */ YY_NO_ANCHOR,
		/* 238 */ YY_NOT_ACCEPT,
		/* 239 */ YY_NO_ANCHOR,
		/* 240 */ YY_NOT_ACCEPT,
		/* 241 */ YY_NO_ANCHOR,
		/* 242 */ YY_NOT_ACCEPT,
		/* 243 */ YY_NO_ANCHOR,
		/* 244 */ YY_NOT_ACCEPT,
		/* 245 */ YY_NO_ANCHOR,
		/* 246 */ YY_NOT_ACCEPT,
		/* 247 */ YY_NOT_ACCEPT,
		/* 248 */ YY_NOT_ACCEPT,
		/* 249 */ YY_NOT_ACCEPT,
		/* 250 */ YY_NOT_ACCEPT,
		/* 251 */ YY_NOT_ACCEPT,
		/* 252 */ YY_NOT_ACCEPT,
		/* 253 */ YY_NOT_ACCEPT,
		/* 254 */ YY_NOT_ACCEPT,
		/* 255 */ YY_NOT_ACCEPT,
		/* 256 */ YY_NO_ANCHOR,
		/* 257 */ YY_NOT_ACCEPT,
		/* 258 */ YY_NO_ANCHOR,
		/* 259 */ YY_NO_ANCHOR,
		/* 260 */ YY_NO_ANCHOR,
		/* 261 */ YY_NO_ANCHOR,
		/* 262 */ YY_NO_ANCHOR,
		/* 263 */ YY_NO_ANCHOR,
		/* 264 */ YY_NOT_ACCEPT,
		/* 265 */ YY_NO_ANCHOR,
		/* 266 */ YY_NO_ANCHOR,
		/* 267 */ YY_NOT_ACCEPT,
		/* 268 */ YY_NO_ANCHOR,
		/* 269 */ YY_NOT_ACCEPT,
		/* 270 */ YY_NO_ANCHOR,
		/* 271 */ YY_NO_ANCHOR,
		/* 272 */ YY_NO_ANCHOR,
		/* 273 */ YY_NO_ANCHOR,
		/* 274 */ YY_NO_ANCHOR,
		/* 275 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"6,75:7,11,7,4,7:2,8,75:18,7,45,73,46,47,48,50,75,2,5,3,37,39,1,33,36,55:10," +
"40,41,38,9,10,51,34,13,72,12,30,16,23,72,26,24,72:2,17,72,25,29,31,72,21,15" +
",27,22,28,32,72:3,53,19,54,49,44,52,56,74,58,59,60,18,57,61,62,57:2,63,57,6" +
"4,65,66,57,67,68,20,69,70,71,57:3,42,14,43,35,75,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,276,
"0,1,2,3,4,1:4,5,1:2,6,7,8,1:6,9,1:16,10,1:5,11,1,12,1:3,13,1,14,1:2,15,1:2," +
"16,1:4,17,1:5,18,1:2,19,1:3,20,1:7,21,22,23,1,24,25,22,1,22,1,22,16,1,15,22" +
",1,26,22:2,26,1,22,3,27,28,26,29,22,30,26,31,26,22,16,32,22,26:2,22,33,26,2" +
"2,26,34,35,36,37,26:2,22,26,38,22,26,22,26:3,39,40,41,22,26,42,26,43,44,45," +
"26,46,47,48,49,50,51,29,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68," +
"69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,14," +
"93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,31" +
",113,114,115,116,117,118,119,120,121,122,123,124,125,16,126,32,127,15,128,4" +
"2,129,130,131,132,133,33,17,134,135,136,137,138,139,140,141,142,143,144,145" +
",146,147,148,149,150,151,152,153,154,155,156,157,158,159")[0];

	private int yy_nxt[][] = unpackFromString(160,76,
"1,2,3,4,5,6,7,8:2,9,10,11,12,85,13,85,256,268,14,15,86,85:2,108,129,271,85," +
"273,85,143,85,274,275,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33" +
",34,35,36,37,38,109:2,130,109,144,109,151,155,260,158,161,109:4,164,85,39,1" +
"09,7,-1:77,40,-1:77,41,-1:77,42,-1:80,43,-1:77,85,150,84,85:2,154,85,-1,85:" +
"13,-1:11,85,-1:10,85,150,85:6,154,85:9,-1,85,-1:14,107,44,128,142,257,45,-1" +
":4,45,87,46,149,-1:2,153,-1:26,107,-1:3,142,149,87,257,46,153,-1:2,128,-1:1" +
"9,109,167,110,109:4,159,109:4,131,109:8,-1:11,109,-1:10,109,167,109:5,131,1" +
"09:10,-1,109,-1:2,48,-1:7,49,-1:121,38,-1:33,189,50,180,192,-1:3,91,-1:3,19" +
"5,-1,198,91,264,201,-1:2,51,-1:23,189,-1:3,192,198,195,-1:2,201,-1:2,180,-1" +
",264,51,-1:18,198,-1:11,198,-1:34,198,-1:26,54,-1,135,236,147,55,-1:7,56,-1" +
":3,238,-1,57,-1:26,54,-1,147,-1:2,55,56,238,57,-1,236,-1:21,57,-1:2,96,-1:1" +
"3,57,-1:31,96,-1:2,57,-1:23,61,-1,61,-1:43,61,-1:29,60:2,-1:52,60,-1:21,250" +
",-1:5,250,-1:6,250,-1:49,69:3,-1,69,-1,69:12,-1,69:53,-1,69:2,-1:4,74,-1:13" +
",75,76,77,-1:43,78,-1:8,79,80,-1:65,81,-1:24,183,186,180,-1:40,183,-1:11,18" +
"0,-1:19,85:2,-1,85:4,-1,85:13,-1:11,85,-1:10,85:18,-1,85,-1:13,109:2,162,10" +
"9:4,165,109,170,109:4,173,109:6,-1:11,109,-1:10,109:6,173,109:5,170,109:5,-" +
"1,109,-1:15,232,-1:11,198,-1,264,-1:32,198,-1:8,264,-1:17,85:2,198,85:4,-1," +
"85:6,263,85:6,-1:11,85,-1:10,85:6,263,85:11,-1,85,-1:13,109:2,-1,109:4,-1,1" +
"09:13,-1:11,109,-1:10,109:18,-1,109,-1:15,180:2,-1:52,180,-1:19,85:2,87,85:" +
"4,-1,85:4,145,85:8,-1:11,85,-1:10,85:7,145,85:10,-1,85,-1:15,225,-1:2,225,-" +
"1,225,-1:43,225,-1:24,109:2,198,109:4,-1,109:6,218,109:6,-1:11,109,-1:10,10" +
"9:6,218,109:11,-1,109,-1:15,57,-1:16,57,-1:34,57,-1:23,247,-1:9,247,-1:37,2" +
"47,-1:27,63,-1:9,249,-1:5,104,-1:28,104,-1:2,249,-1:18,67,-1:83,204,207,-1:" +
"13,264,-1:27,204,-1:13,264,-1:17,85:2,88,163,85:2,111,-1,85:3,111,85,89,85:" +
"7,-1:11,85,-1:10,85:9,89,85:3,163,85:4,-1,85,-1:13,109,176,84,109:2,179,109" +
",-1,109:13,-1:11,109,-1:10,109,176,109:6,179,109:9,-1,109,-1:15,123,60,61,-" +
"1:4,267,-1:2,247,-1:35,61,-1,247,-1:4,267,60,-1:21,91,-1:5,91,-1:6,91,-1:4," +
"51,-1:38,51,-1:16,85:2,47,85:3,90,-1,85:3,90,85:9,-1:11,85,-1:10,85:18,-1,8" +
"5,-1:13,109:2,269,266,109,265,109,-1,109:13,-1:11,109,-1:10,109:8,265,109:4" +
",266,109:4,-1,109,-1:15,267,-1:6,267,-1:45,267,-1:22,216,-1,192,-1:7,195,-1" +
":35,192,-1,195,-1:25,85:2,180,178,85:3,-1,85:13,-1:11,85,-1:10,85:13,178,85" +
":4,-1,85,-1:13,109:2,88,182,109:2,132,-1,109:3,132,109,112,109:7,-1:11,109," +
"-1:10,109:9,112,109:3,182,109:4,-1,109,-1:15,52,-1:5,93,-1:6,93,-1,201,-1:3" +
"5,201,-1:22,85,181,183,85:4,-1,85:13,-1:11,85,-1:10,85,181,85:16,-1,85,-1:1" +
"3,109:2,156,109,185,109:2,-1,109:9,262,109:3,-1:11,109,-1:10,109:5,185,109:" +
"4,262,109:7,-1,109,-1:15,114,-1:5,91,-1:6,91,-1,222,-1:35,222,-1:22,85:2,91" +
",85:4,-1,133,85:6,133,85:5,-1:11,85,-1:10,85:18,-1,85,-1:13,109:2,47,109:3," +
"113,-1,109:3,113,109:9,-1:11,109,-1:10,109:18,-1,109,-1:13,85:2,222,85:4,-1" +
",85:9,190,85:3,-1:11,85,-1:10,85:10,190,85:7,-1,85,-1:13,109:2,174,109:4,-1" +
",109:9,270,109:3,-1:11,109,-1:10,109:10,270,109:7,-1,109,-1:15,228,-1,192,-" +
"1:2,230,-1:2,230,-1:37,192,-1:8,230,-1:18,85:2,264,85:4,-1,85:8,193,85:4,-1" +
":11,85,-1:10,85:15,193,85:2,-1,85,-1:13,109:2,177,109:4,-1,109:6,194,109:6," +
"-1:11,109,-1:10,109:6,194,109:11,-1,109,-1:15,230,-1:4,230,-1:2,230,-1:46,2" +
"30,-1:18,85:2,51,85:4,-1,85:12,92,-1:11,85,-1:10,85:16,92,85,-1,85,-1:13,10" +
"9:2,225,109:2,197,109,225,109:13,-1:11,109,-1:10,109:8,197,109:9,-1,109,-1:" +
"15,51,-1:5,93,-1:6,93,-1:4,51,-1:38,51,-1:16,85:2,93,85:4,-1,116,85:6,116,8" +
"5:5,-1:11,85,-1:10,85:18,-1,85,-1:13,109:2,230,109:4,230,109:2,200,109:10,-" +
"1:11,109,-1:10,109:14,200,109:3,-1,109,-1:15,192,-1,192,-1:43,192,-1:27,85:" +
"2,192,85,196,85:2,-1,85:13,-1:11,85,-1:10,85:5,196,85:12,-1,85,-1:13,109:2," +
"192,109,203,109:2,-1,109:13,-1:11,109,-1:10,109:5,203,109:12,-1,109,-1:15,2" +
"34,-1:14,234,-1:35,234,-1:22,85:2,195,85:4,-1,85:4,202,85:8,-1:11,85,-1:10," +
"85:7,202,85:10,-1,85,-1:13,109:2,180,206,109:3,-1,109:13,-1:11,109,-1:10,10" +
"9:13,206,109:4,-1,109,-1:15,195,-1:9,195,-1:37,195,-1:25,85:2,53,85,94,85:2" +
",-1,85:13,-1:11,85,-1:10,85:5,94,85:12,-1,85,-1:13,109,209,183,109:4,-1,109" +
":13,-1:11,109,-1:10,109,209,109:16,-1,109,-1:15,53,-1,53,-1:43,53,-1:27,85:" +
"2,236,205,85:3,-1,85:13,-1:11,85,-1:10,85:13,205,85:4,-1,85,-1:13,109:2,264" +
",109:4,-1,109:8,272,109:4,-1:11,109,-1:10,109:15,272,109:2,-1,109,-1:15,236" +
":2,-1:52,236,-1:19,136,85,54,85:4,-1,85:13,-1:11,85,-1:10,85:3,136,85:14,-1" +
",85,-1:13,109:2,91,109:4,-1,146,109:6,146,109:5,-1:11,109,-1:10,109:18,-1,1" +
"09,-1:15,117,236,53,-1:43,53,-1:7,236,-1:19,85:2,99,85,122,85:2,-1,85:13,-1" +
":11,85,-1:10,85:5,122,85:12,-1,85,-1:13,109:2,51,109:4,-1,109:12,115,-1:11," +
"109,-1:10,109:16,115,109,-1,109,-1:13,54,-1,95,236,-1:42,54,-1:9,236,-1:19," +
"85:2,57,85:4,-1,85:11,98,85,-1:11,85,-1:10,85:11,98,85:6,-1,85,-1:13,109:2," +
"93,109:4,-1,134,109:6,134,109:5,-1:11,109,-1:10,109:18,-1,109,-1:15,56,-1:1" +
"0,56,-1:38,56,-1:23,85:2,238,85:4,-1,85:9,208,85:3,-1:11,85,-1:10,85:10,208" +
",85:7,-1,85,-1:13,109:2,195,109:4,-1,109:4,227,109:8,-1:11,109,-1:10,109:7," +
"227,109:10,-1,109,-1:15,240,-1:2,240,-1:45,240,-1:24,85:2,56,85:4,-1,85:5,1" +
"38,85:7,-1:11,85,-1:10,85:9,138,85:8,-1,85,-1:13,109:2,244,229,109:3,244,10" +
"9:13,-1:11,109,-1:10,109:13,229,109:4,-1,109,-1:15,242,-1,242,-1:43,242,-1:" +
"27,85:2,96,85:2,119,85,-1,85:13,-1:11,85,-1:10,85:8,119,85:9,-1,85,-1:13,10" +
"9:2,59,109,100,109:2,59,109:13,-1:11,109,-1:10,109:5,100,109:12,-1,109,-1:1" +
"3,85:2,240,85:2,214,85,-1,85:13,-1:11,85,-1:10,85:8,214,85:9,-1,85,-1:13,10" +
"9:2,56,109:4,-1,109:5,120,109:7,-1:11,109,-1:10,109:9,120,109:8,-1,109,-1:1" +
"3,54,-1,54,-1:43,54,-1:29,85:2,60,101,85:3,-1,85:13,-1:11,85,-1:10,85:13,10" +
"1,85:4,-1,85,-1:13,109:2,53,109,152,109:2,-1,109:13,-1:11,109,-1:10,109:5,1" +
"52,109:12,-1,109,-1:13,54,-1,118,-1:14,238,-1:28,54,-1:6,238,-1:22,85:2,247" +
",85:4,-1,85:4,217,85:8,-1:11,85,-1:10,85:7,217,85:10,-1,85,-1:13,109:2,236," +
"231,109:3,-1,109:13,-1:11,109,-1:10,109:13,231,109:4,-1,109,-1:15,58,236,99" +
",-1:43,99,-1:7,236,-1:19,85:2,267,85:4,-1,85,220,85:11,-1:11,85,-1:10,85:12" +
",220,85:5,-1,85,-1:13,148,109,54,109:4,-1,109:13,-1:11,109,-1:10,109:3,148," +
"109:14,-1,109,-1:15,99,-1,99,-1:43,99,-1:27,85:2,61,85,102,85:2,-1,85:13,-1" +
":11,85,-1:10,85:5,102,85:12,-1,85,-1:13,109:2,99,109,139,109:2,-1,109:13,-1" +
":11,109,-1:10,109:5,139,109:12,-1,109,-1:15,97,-1:2,240,-1:7,56,-1:37,240,5" +
"6,-1:23,85:2,104,85:4,-1,85:10,125,85:2,-1:11,85,-1:10,85:4,125,85:13,-1,85" +
",-1:13,109:2,242,109,235,109:2,-1,109:13,-1:11,109,-1:10,109:5,235,109:12,-" +
"1,109,-1:13,54,-1,99,-1,99,-1:41,54,-1,99,-1:27,85:2,249,85:4,-1,85:4,223,8" +
"5:8,-1:11,85,-1:10,85:7,223,85:10,-1,85,-1:13,109:2,57,109:4,-1,109:11,121," +
"109,-1:11,109,-1:10,109:11,121,109:6,-1,109,-1:13,85:2,250,85:4,-1,226,85:6" +
",226,85:5,-1:11,85,-1:10,85:18,-1,85,-1:13,109:2,96,109:2,137,109,-1,109:13" +
",-1:11,109,-1:10,109:8,137,109:9,-1,109,-1:15,244:2,-1:3,244,-1:48,244,-1:1" +
"9,85:2,64,105,85:3,-1,85:13,-1:11,85,-1:10,85:13,105,85:4,-1,85,-1:13,109:2" +
",240,109:2,237,109,-1,109:13,-1:11,109,-1:10,109:8,237,109:9,-1,109,-1:15,5" +
"9,-1,59,-1:2,59,-1:5,56,-1:34,59,-1:3,56,-1:23,109:2,62,109,103,109:2,62,10" +
"9:13,-1:11,109,-1:10,109:5,103,109:12,-1,109,-1:15,59,-1,59,-1:2,59,-1:40,5" +
"9,-1:27,109:2,60,140,109:3,-1,109:13,-1:11,109,-1:10,109:13,140,109:4,-1,10" +
"9,-1:15,246,-1,242,-1:12,238,-1:30,242,-1:4,238,-1:22,109:2,247,109:4,-1,10" +
"9:4,239,109:8,-1:11,109,-1:10,109:7,239,109:10,-1,109,-1:15,96,-1:2,96,-1:4" +
"5,96,-1:24,109:2,267,109:4,-1,109,241,109:11,-1:11,109,-1:10,109:12,241,109" +
":5,-1,109,-1:13,109:2,61,109,124,109:2,-1,109:13,-1:11,109,-1:10,109:5,124," +
"109:12,-1,109,-1:13,109:2,104,109:4,-1,109:10,141,109:2,-1:11,109,-1:10,109" +
":4,141,109:13,-1,109,-1:13,109:2,249,109:4,-1,109:4,243,109:8,-1:11,109,-1:" +
"10,109:7,243,109:10,-1,109,-1:13,109:2,250,109:4,-1,245,109:6,245,109:5,-1:" +
"11,109,-1:10,109:18,-1,109,-1:15,62,-1,62,-1:2,62,-1:40,62,-1:27,109:2,64,1" +
"26,109:3,-1,109:13,-1:11,109,-1:10,109:13,126,109:4,-1,109,-1:15,248,-1:6,2" +
"67,-1:2,247,-1:37,247,-1:4,267,-1:22,104,-1:15,104,-1:28,104,-1:30,64:2,-1:" +
"52,64,-1:7,1,65,106,127,66,65:3,-1,65:67,1,65:3,68,65:3,-1,65:67,1,7:3,-1,7" +
":3,-1,7:67,1,69:3,70,69,71,69:12,72,69:53,73,69:2,1,82:3,83,82:3,-1,82:64,8" +
"3,82:2,-1:12,85:2,269,259,85,258,85,-1,85:13,-1:11,85,-1:10,85:8,258,85:4,2" +
"59,85:4,-1,85,-1:14,183,210,213,-1:40,183,-1:11,213,-1:19,85:2,213,187,85:3" +
",-1,85:13,-1:11,85,-1:10,85:13,187,85:4,-1,85,-1:13,85,184,204,85:4,-1,85:1" +
"3,-1:11,85,-1:10,85,184,85:16,-1,85,-1:13,109:2,168,109,188,109:2,-1,109:9," +
"191,109:3,-1:11,109,-1:10,109:5,188,109:4,191,109:7,-1,109,-1:13,85:2,234,8" +
"5:4,-1,85:9,199,85:3,-1:11,85,-1:10,85:10,199,85:7,-1,85,-1:13,109:2,222,10" +
"9:4,-1,109:9,221,109:3,-1:11,109,-1:10,109:10,221,109:7,-1,109,-1:13,85:2,2" +
"42,85,211,85:2,-1,85:13,-1:11,85,-1:10,85:5,211,85:12,-1,85,-1:15,238,-1:14" +
",238,-1:35,238,-1:22,109:2,213,215,109:3,-1,109:13,-1:11,109,-1:10,109:13,2" +
"15,109:4,-1,109,-1:13,109,212,204,109:4,-1,109:13,-1:11,109,-1:10,109,212,1" +
"09:16,-1,109,-1:15,249,-1:9,249,-1:37,249,-1:25,85:2,156,85,157,85:2,-1,85:" +
"9,160,85:3,-1:11,85,-1:10,85:5,157,85:4,160,85:7,-1,85,-1:14,204,219,213,-1" +
":40,204,-1:11,213,-1:19,109:2,234,109:4,-1,109:9,224,109:3,-1:11,109,-1:10," +
"109:10,224,109:7,-1,109,-1:13,85:2,168,85,166,85:2,-1,85:9,169,85:3,-1:11,8" +
"5,-1:10,85:5,166,85:4,169,85:7,-1,85,-1:13,109:2,238,109:4,-1,109:9,233,109" +
":3,-1:11,109,-1:10,109:10,233,109:7,-1,109,-1:13,85:2,171,85:4,-1,85:6,172," +
"85:6,-1:11,85,-1:10,85:6,172,85:11,-1,85,-1:13,85:2,174,85:4,-1,85:9,261,85" +
":3,-1:11,85,-1:10,85:10,261,85:7,-1,85,-1:13,85:2,177,85:4,-1,85:6,175,85:6" +
",-1:11,85,-1:10,85:6,175,85:11,-1,85,-1");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */
    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
		case COMMENT:
	    	yybegin(ESTOFERROR);	
	   		return new Symbol(TokenConstants.ERROR, "EOF in comment");
	   	case STR:
	   		yybegin(ESTOFERROR);
	   		return new Symbol(TokenConstants.ERROR, "EOF in string constant");
	   case ESTOFERROR:
	   break;
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return new Symbol(TokenConstants.MINUS); }
					case -3:
						break;
					case 3:
						{return new Symbol(TokenConstants.LPAREN); }
					case -4:
						break;
					case 4:
						{return new Symbol(TokenConstants.MULT); }
					case -5:
						break;
					case 5:
						{curr_lineno++;}
					case -6:
						break;
					case 6:
						{return new Symbol(TokenConstants.RPAREN); }
					case -7:
						break;
					case 7:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  return new Symbol(TokenConstants.ERROR, yytext());}
					case -8:
						break;
					case 8:
						{}
					case -9:
						break;
					case 9:
						{return new Symbol(TokenConstants.EQ); }
					case -10:
						break;
					case 10:
						{return new Symbol(TokenConstants.ERROR, ">");}
					case -11:
						break;
					case 11:
						{}
					case -12:
						break;
					case 12:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -13:
						break;
					case 13:
						{return new Symbol(TokenConstants.ERROR, "|");}
					case -14:
						break;
					case 14:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -15:
						break;
					case 15:
						{return new Symbol(TokenConstants.ERROR, "\\");}
					case -16:
						break;
					case 16:
						{return new Symbol(TokenConstants.DOT); }
					case -17:
						break;
					case 17:
						{return new Symbol(TokenConstants.AT); }
					case -18:
						break;
					case 18:
						{return new Symbol(TokenConstants.NEG); }
					case -19:
						break;
					case 19:
						{return new Symbol(TokenConstants.DIV); }
					case -20:
						break;
					case 20:
						{return new Symbol(TokenConstants.PLUS); }
					case -21:
						break;
					case 21:
						{return new Symbol(TokenConstants.LT); }
					case -22:
						break;
					case 22:
						{return new Symbol(TokenConstants.COMMA); }
					case -23:
						break;
					case 23:
						{return new Symbol(TokenConstants.COLON); }
					case -24:
						break;
					case 24:
						{return new Symbol(TokenConstants.SEMI); }
					case -25:
						break;
					case 25:
						{return new Symbol(TokenConstants.LBRACE); }
					case -26:
						break;
					case 26:
						{return new Symbol(TokenConstants.RBRACE); }
					case -27:
						break;
					case 27:
						{return new Symbol(TokenConstants.ERROR, "_");}
					case -28:
						break;
					case 28:
						{return new Symbol(TokenConstants.ERROR, "!");}
					case -29:
						break;
					case 29:
						{return new Symbol(TokenConstants.ERROR, "#");}
					case -30:
						break;
					case 30:
						{return new Symbol(TokenConstants.ERROR, "$");}
					case -31:
						break;
					case 31:
						{return new Symbol(TokenConstants.ERROR, "%");}
					case -32:
						break;
					case 32:
						{return new Symbol(TokenConstants.ERROR, "^");}
					case -33:
						break;
					case 33:
						{return new Symbol(TokenConstants.ERROR, "&");}
					case -34:
						break;
					case 34:
						{return new Symbol(TokenConstants.ERROR, "?");}
					case -35:
						break;
					case 35:
						{return new Symbol(TokenConstants.ERROR, "`");}
					case -36:
						break;
					case 36:
						{return new Symbol(TokenConstants.ERROR, "[");}
					case -37:
						break;
					case 37:
						{return new Symbol(TokenConstants.ERROR, "]");}
					case -38:
						break;
					case 38:
						{	AbstractSymbol inte = AbstractTable.inttable.addString(yytext());
							return new Symbol(TokenConstants.INT_CONST,inte); }
					case -39:
						break;
					case 39:
						{yybegin(STR); string_buf.setLength(0); }
					case -40:
						break;
					case 40:
						{yybegin(COMMENTLINEAL);}
					case -41:
						break;
					case 41:
						{yybegin(COMMENT);comentario++;}
					case -42:
						break;
					case 42:
						{return new Symbol(TokenConstants.ERROR, " Unmatched *)");}
					case -43:
						break;
					case 43:
						{return new Symbol(TokenConstants.DARROW); }
					case -44:
						break;
					case 44:
						{return new Symbol(TokenConstants.FI);}
					case -45:
						break;
					case 45:
						{return new Symbol(TokenConstants.IF);}
					case -46:
						break;
					case 46:
						{return new Symbol(TokenConstants.IN);}
					case -47:
						break;
					case 47:
						{return new Symbol(TokenConstants.OF);}
					case -48:
						break;
					case 48:
						{return new Symbol(TokenConstants.ASSIGN); }
					case -49:
						break;
					case 49:
						{return new Symbol(TokenConstants.LE); }
					case -50:
						break;
					case 50:
						{return new Symbol(TokenConstants.LET);}
					case -51:
						break;
					case 51:
						{return new Symbol(TokenConstants.NEW);}
					case -52:
						break;
					case 52:
						{return new Symbol(TokenConstants.NOT); }
					case -53:
						break;
					case 53:
						{return new Symbol(TokenConstants.CASE);}
					case -54:
						break;
					case 54:
						{return new Symbol(TokenConstants.ESAC);}
					case -55:
						break;
					case 55:
						{return new Symbol(TokenConstants.POOL);}
					case -56:
						break;
					case 56:
						{return new Symbol(TokenConstants.THEN);}
					case -57:
						break;
					case 57:
						{return new Symbol(TokenConstants.LOOP);}
					case -58:
						break;
					case 58:
						{return new Symbol(TokenConstants.ELSE);}
					case -59:
						break;
					case 59:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -60:
						break;
					case 60:
						{return new Symbol(TokenConstants.CLASS);}
					case -61:
						break;
					case 61:
						{return new Symbol(TokenConstants.WHILE);}
					case -62:
						break;
					case 62:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -63:
						break;
					case 63:
						{return new Symbol(TokenConstants.ISVOID);}
					case -64:
						break;
					case 64:
						{return new Symbol(TokenConstants.INHERITS);}
					case -65:
						break;
					case 65:
						{}
					case -66:
						break;
					case 66:
						{curr_lineno++;}
					case -67:
						break;
					case 67:
						{if (--comentario == 0) {yybegin(YYINITIAL);}}
					case -68:
						break;
					case 68:
						{curr_lineno++; yybegin(YYINITIAL);}
					case -69:
						break;
					case 69:
						{string_buf.append(yytext());}
					case -70:
						break;
					case 70:
						{yybegin(YYINITIAL); string_buf.setLength(0); return new Symbol(TokenConstants.ERROR, "Undetermined string constant");}
					case -71:
						break;
					case 71:
						{yybegin(STRERROR); return new Symbol(TokenConstants.ERROR, "String contains null character");}
					case -72:
						break;
					case 72:
						{}
					case -73:
						break;
					case 73:
						{yybegin(YYINITIAL); return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(string_buf.toString()));}
					case -74:
						break;
					case 74:
						{string_buf.append("\n");}
					case -75:
						break;
					case 75:
						{string_buf.append("\f");}
					case -76:
						break;
					case 76:
						{string_buf.append("\\");}
					case -77:
						break;
					case 77:
						{string_buf.append("\t");}
					case -78:
						break;
					case 78:
						{string_buf.append("\n");curr_lineno++; }
					case -79:
						break;
					case 79:
						{string_buf.append("\"");}
					case -80:
						break;
					case 80:
						{string_buf.append("\b");}
					case -81:
						break;
					case 81:
						{string_buf.append("\\n");}
					case -82:
						break;
					case 82:
						{}
					case -83:
						break;
					case 83:
						{yybegin(YYINITIAL);}
					case -84:
						break;
					case 85:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -85:
						break;
					case 86:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -86:
						break;
					case 87:
						{return new Symbol(TokenConstants.FI);}
					case -87:
						break;
					case 88:
						{return new Symbol(TokenConstants.IF);}
					case -88:
						break;
					case 89:
						{return new Symbol(TokenConstants.IN);}
					case -89:
						break;
					case 90:
						{return new Symbol(TokenConstants.OF);}
					case -90:
						break;
					case 91:
						{return new Symbol(TokenConstants.LET);}
					case -91:
						break;
					case 92:
						{return new Symbol(TokenConstants.NEW);}
					case -92:
						break;
					case 93:
						{return new Symbol(TokenConstants.NOT); }
					case -93:
						break;
					case 94:
						{return new Symbol(TokenConstants.CASE);}
					case -94:
						break;
					case 95:
						{return new Symbol(TokenConstants.ESAC);}
					case -95:
						break;
					case 96:
						{return new Symbol(TokenConstants.POOL);}
					case -96:
						break;
					case 97:
						{return new Symbol(TokenConstants.THEN);}
					case -97:
						break;
					case 98:
						{return new Symbol(TokenConstants.LOOP);}
					case -98:
						break;
					case 99:
						{return new Symbol(TokenConstants.ELSE);}
					case -99:
						break;
					case 100:
						{return new Symbol(TokenConstants.BOOL_CONST, "true"); }
					case -100:
						break;
					case 101:
						{return new Symbol(TokenConstants.CLASS);}
					case -101:
						break;
					case 102:
						{return new Symbol(TokenConstants.WHILE);}
					case -102:
						break;
					case 103:
						{return new Symbol(TokenConstants.BOOL_CONST, "false"); }
					case -103:
						break;
					case 104:
						{return new Symbol(TokenConstants.ISVOID);}
					case -104:
						break;
					case 105:
						{return new Symbol(TokenConstants.INHERITS);}
					case -105:
						break;
					case 106:
						{}
					case -106:
						break;
					case 108:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -107:
						break;
					case 109:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -108:
						break;
					case 110:
						{return new Symbol(TokenConstants.FI);}
					case -109:
						break;
					case 111:
						{return new Symbol(TokenConstants.IF);}
					case -110:
						break;
					case 112:
						{return new Symbol(TokenConstants.IN);}
					case -111:
						break;
					case 113:
						{return new Symbol(TokenConstants.OF);}
					case -112:
						break;
					case 114:
						{return new Symbol(TokenConstants.LET);}
					case -113:
						break;
					case 115:
						{return new Symbol(TokenConstants.NEW);}
					case -114:
						break;
					case 116:
						{return new Symbol(TokenConstants.NOT); }
					case -115:
						break;
					case 117:
						{return new Symbol(TokenConstants.CASE);}
					case -116:
						break;
					case 118:
						{return new Symbol(TokenConstants.ESAC);}
					case -117:
						break;
					case 119:
						{return new Symbol(TokenConstants.POOL);}
					case -118:
						break;
					case 120:
						{return new Symbol(TokenConstants.THEN);}
					case -119:
						break;
					case 121:
						{return new Symbol(TokenConstants.LOOP);}
					case -120:
						break;
					case 122:
						{return new Symbol(TokenConstants.ELSE);}
					case -121:
						break;
					case 123:
						{return new Symbol(TokenConstants.CLASS);}
					case -122:
						break;
					case 124:
						{return new Symbol(TokenConstants.WHILE);}
					case -123:
						break;
					case 125:
						{return new Symbol(TokenConstants.ISVOID);}
					case -124:
						break;
					case 126:
						{return new Symbol(TokenConstants.INHERITS);}
					case -125:
						break;
					case 127:
						{}
					case -126:
						break;
					case 129:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -127:
						break;
					case 130:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -128:
						break;
					case 131:
						{return new Symbol(TokenConstants.FI);}
					case -129:
						break;
					case 132:
						{return new Symbol(TokenConstants.IF);}
					case -130:
						break;
					case 133:
						{return new Symbol(TokenConstants.LET);}
					case -131:
						break;
					case 134:
						{return new Symbol(TokenConstants.NOT); }
					case -132:
						break;
					case 135:
						{return new Symbol(TokenConstants.CASE);}
					case -133:
						break;
					case 136:
						{return new Symbol(TokenConstants.ESAC);}
					case -134:
						break;
					case 137:
						{return new Symbol(TokenConstants.POOL);}
					case -135:
						break;
					case 138:
						{return new Symbol(TokenConstants.THEN);}
					case -136:
						break;
					case 139:
						{return new Symbol(TokenConstants.ELSE);}
					case -137:
						break;
					case 140:
						{return new Symbol(TokenConstants.CLASS);}
					case -138:
						break;
					case 141:
						{return new Symbol(TokenConstants.ISVOID);}
					case -139:
						break;
					case 143:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -140:
						break;
					case 144:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -141:
						break;
					case 145:
						{return new Symbol(TokenConstants.FI);}
					case -142:
						break;
					case 146:
						{return new Symbol(TokenConstants.LET);}
					case -143:
						break;
					case 147:
						{return new Symbol(TokenConstants.CASE);}
					case -144:
						break;
					case 148:
						{return new Symbol(TokenConstants.ESAC);}
					case -145:
						break;
					case 150:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -146:
						break;
					case 151:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -147:
						break;
					case 152:
						{return new Symbol(TokenConstants.CASE);}
					case -148:
						break;
					case 154:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -149:
						break;
					case 155:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -150:
						break;
					case 157:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -151:
						break;
					case 158:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -152:
						break;
					case 160:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -153:
						break;
					case 161:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -154:
						break;
					case 163:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -155:
						break;
					case 164:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -156:
						break;
					case 166:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -157:
						break;
					case 167:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -158:
						break;
					case 169:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -159:
						break;
					case 170:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -160:
						break;
					case 172:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -161:
						break;
					case 173:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -162:
						break;
					case 175:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -163:
						break;
					case 176:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -164:
						break;
					case 178:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -165:
						break;
					case 179:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -166:
						break;
					case 181:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -167:
						break;
					case 182:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -168:
						break;
					case 184:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -169:
						break;
					case 185:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -170:
						break;
					case 187:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -171:
						break;
					case 188:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -172:
						break;
					case 190:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -173:
						break;
					case 191:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -174:
						break;
					case 193:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -175:
						break;
					case 194:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -176:
						break;
					case 196:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -177:
						break;
					case 197:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -178:
						break;
					case 199:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -179:
						break;
					case 200:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -180:
						break;
					case 202:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -181:
						break;
					case 203:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -182:
						break;
					case 205:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -183:
						break;
					case 206:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -184:
						break;
					case 208:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -185:
						break;
					case 209:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -186:
						break;
					case 211:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -187:
						break;
					case 212:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -188:
						break;
					case 214:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -189:
						break;
					case 215:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -190:
						break;
					case 217:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -191:
						break;
					case 218:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -192:
						break;
					case 220:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -193:
						break;
					case 221:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -194:
						break;
					case 223:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -195:
						break;
					case 224:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -196:
						break;
					case 226:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -197:
						break;
					case 227:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -198:
						break;
					case 229:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -199:
						break;
					case 231:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -200:
						break;
					case 233:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -201:
						break;
					case 235:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -202:
						break;
					case 237:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -203:
						break;
					case 239:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -204:
						break;
					case 241:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -205:
						break;
					case 243:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -206:
						break;
					case 245:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -207:
						break;
					case 256:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -208:
						break;
					case 258:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -209:
						break;
					case 259:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -210:
						break;
					case 260:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -211:
						break;
					case 261:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -212:
						break;
					case 262:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -213:
						break;
					case 263:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -214:
						break;
					case 265:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -215:
						break;
					case 266:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -216:
						break;
					case 268:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -217:
						break;
					case 270:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -218:
						break;
					case 271:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -219:
						break;
					case 272:
						{	AbstractSymbol obj = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.OBJECTID,obj); }
					case -220:
						break;
					case 273:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -221:
						break;
					case 274:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -222:
						break;
					case 275:
						{	AbstractSymbol tip = AbstractTable.idtable.addString(yytext());
							return new Symbol(TokenConstants.TYPEID,tip); }
					case -223:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
