// @ Grab("thymeleaf-spring4")

@Controller
class Application {

	@RequestMapping(value="/greeting", method=RequestMethod.GET, produces="text/plain")
	@ResponseBody
	public String greeting(@RequestParam(name="id", required=true) int id) {
		// model.addAttribute("name", name)
		return "greeting:"+id;
	}
}