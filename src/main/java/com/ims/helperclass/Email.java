package com.ims.helperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
 @ToString
public class Email {
 
    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
