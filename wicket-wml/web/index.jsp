<?xml version="1.0"?>
<%-- <%@page contentType="text/xml" pageEncoding="UTF-8"%>--%>
<%! int counter=0;%>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "file:///c:/wml_1.1.xml">
<wml>
        <card id="card_1" title="first card">
                <p> This is card 1(visit:<%=counter %>)<br/>
                        <input type="text" name="var_input" size="5" title="input tag" value="temp_value" /> 


                        <i>
                                <a href="#card_2"> goto card_2</a> <br/>
                        </i>


                        <b>
                                <a href="#card_3"> goto card_3</a> <br/>
                        </b>


                        <anchor title="this is anchor example"> anchor 
                                <go href="#card_2"> 
                                        <setvar name="var_input_another" value="new value"/>
                                </go>
                        </anchor>


                        <do label="do_example" type="accept">
                                <go href="#card_3"> 
                                        <postfield name="var_input_another" value="new value"/>
                                </go>
                        </do>
                        <select name="varCountry">
                                    <optgroup title="Europe">
                                        <option value="AT">Austria</option>
                                        <option value="DE">Germany</option>
                                    </optgroup> 
                                    <optgroup title="Asia">
                                        <option value="CN">China</option>
                                        <option value="JP">Japan</option>
                                    </optgroup>
                                <option value="RU">Russia</option>
                        </select>

                        <select name="select_value" title="Choice Card">
                                <option onpick="#card_1" value="#card_1" >card_1</option>
                                <option onpick="#card_2" value="#card_2" >card_2</option>
                                <option onpick="#card_3" value="#card_3" >card_3</option>
                        </select>
                </p>
                        
        </card>
        <card id="card_2" title="second card">
                <p> This is card 2($(var_input)) and ($(var_input_another))<br/>
                        <a href="#card_1"> go to card_1</a>
                </p>
        </card>
        <card id="card_3" title="third card">
                <p>
                        Param $(param_1)<br/>
                        <table> 
                                <tr>
                                        <td>Column_1</td>
                                        <td>Column_2</td>
                                        <td>Column_3</td>
                                </tr>
                                <tr>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>3</td>
                                </tr>
                        </table>
                        <a href="#card_1"> goto card_1 </a><br/>
                        <a href="#card_2"> goto card_2 </a>
                </p>
        </card>
</wml>