/* -- will reference this later --

import { shallowMount } from "@vue/test-utils";
import HelloWorld from "@/components/HelloWorld.vue";

describe("HelloWorld.vue", () => {
  it("renders props.msg when passed", () => {
    const msg = "new message";
    const wrapper = shallowMount(HelloWorld, {
      props: { msg },
    });
    expect(wrapper.text()).toMatch(msg);
  });
});
*/

describe("Example", () => {
  it("Dumb test (always passes)", () => {
    expect("TEST".toLowerCase()).toMatch("test");
  });
});
